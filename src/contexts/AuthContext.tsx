import React, { createContext, useContext, useReducer, useEffect } from 'react';
import { AuthContextType, AuthState, LoginCredentials, RegisterData, User } from '@/types/auth';
import { authService } from '@/services/auth';

const AuthContext = createContext<AuthContextType | undefined>(undefined);

type AuthAction =
  | { type: 'LOGIN_START' }
  | { type: 'LOGIN_SUCCESS'; payload: User }
  | { type: 'LOGIN_FAILURE'; payload: string }
  | { type: 'LOGOUT' }
  | { type: 'UPDATE_PROFILE'; payload: Partial<User> }
  | { type: 'CLEAR_ERROR' }
  | { type: 'SET_LOADING'; payload: boolean };

const authReducer = (state: AuthState, action: AuthAction): AuthState => {
  switch (action.type) {
    case 'LOGIN_START':
      return { ...state, isLoading: true, error: null };
    case 'LOGIN_SUCCESS':
      return {
        ...state,
        user: action.payload,
        isAuthenticated: true,
        isLoading: false,
        error: null,
      };
    case 'LOGIN_FAILURE':
      return {
        ...state,
        user: null,
        isAuthenticated: false,
        isLoading: false,
        error: action.payload,
      };
    case 'LOGOUT':
      return {
        ...state,
        user: null,
        isAuthenticated: false,
        isLoading: false,
        error: null,
      };
    case 'UPDATE_PROFILE':
      return {
        ...state,
        user: state.user ? { ...state.user, ...action.payload } : null,
      };
    case 'CLEAR_ERROR':
      return { ...state, error: null };
    case 'SET_LOADING':
      return { ...state, isLoading: action.payload };
    default:
      return state;
  }
};

const initialState: AuthState = {
  user: null,
  isAuthenticated: false,
  isLoading: false,
  error: null,
};

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [state, dispatch] = useReducer(authReducer, initialState);

  // Check authentication status on app start and listen to auth state changes
  useEffect(() => {
    const checkAuthStatus = async () => {
      try {
        dispatch({ type: 'SET_LOADING', payload: true });
        
        // Check if user is authenticated with Supabase
        const isAuth = await authService.isAuthenticated();
        if (isAuth) {
          // Try to get current user
          const user = await authService.getCurrentUser();
          if (user) {
            dispatch({ type: 'LOGIN_SUCCESS', payload: user });
          }
        }
      } catch (error) {
        console.error('Auth check failed:', error);
        dispatch({ type: 'LOGOUT' });
      } finally {
        dispatch({ type: 'SET_LOADING', payload: false });
      }
    };

    checkAuthStatus();

    // Listen to Supabase auth state changes
    const { data: { subscription } } = authService.onAuthStateChange(async (event, session) => {
      console.log('Auth state changed:', event, session?.user?.email);
      
      if (event === 'SIGNED_IN' && session?.user) {
        const user = await authService.getCurrentUser();
        if (user) {
          dispatch({ type: 'LOGIN_SUCCESS', payload: user });
        }
      } else if (event === 'SIGNED_OUT') {
        dispatch({ type: 'LOGOUT' });
      }
    });

    return () => subscription.unsubscribe();
  }, []);

  const login = async (credentials: LoginCredentials): Promise<void> => {
    console.log('🔐 AuthContext: Login started');
    dispatch({ type: 'LOGIN_START' });

    try {
      console.log('🔐 AuthContext: Calling authService.signIn');
      const { user, error } = await authService.signIn(credentials);
      
      if (error) {
        console.error('❌ AuthContext: Login failed:', error);
        dispatch({ type: 'LOGIN_FAILURE', payload: error });
        return;
      }
      
      if (user) {
        console.log('✅ AuthContext: Login successful, user:', user);
        dispatch({ type: 'LOGIN_SUCCESS', payload: user });
        console.log('✅ AuthContext: State updated with user');
      } else {
        dispatch({ type: 'LOGIN_FAILURE', payload: 'Login failed' });
      }
    } catch (error) {
      console.error('❌ AuthContext: Login failed:', error);
      const errorMessage = error instanceof Error ? error.message : 'Login failed';
      dispatch({ type: 'LOGIN_FAILURE', payload: errorMessage });
      throw error;
    }
  };

  const register = async (data: RegisterData): Promise<void> => {
    dispatch({ type: 'LOGIN_START' });

    try {
      const { user, error } = await authService.signUp(data);
      
      if (error) {
        dispatch({ type: 'LOGIN_FAILURE', payload: error });
        return;
      }
      
      if (user) {
        dispatch({ type: 'LOGIN_SUCCESS', payload: user });
      } else {
        dispatch({ type: 'LOGIN_FAILURE', payload: 'Registration failed' });
      }
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Registration failed';
      dispatch({ type: 'LOGIN_FAILURE', payload: errorMessage });
      throw error;
    }
  };

  const logout = async (): Promise<void> => {
    try {
      const { error } = await authService.signOut();
      if (error) {
        console.error('Logout error:', error);
      }
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      dispatch({ type: 'LOGOUT' });
    }
  };

  const updateProfile = async (data: Partial<User>): Promise<void> => {
    try {
      const { user, error } = await authService.updateProfile(data);
      
      if (error) {
        const errorMessage = error;
        dispatch({ type: 'LOGIN_FAILURE', payload: errorMessage });
        return;
      }
      
      if (user) {
        dispatch({ type: 'UPDATE_PROFILE', payload: user });
      }
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Profile update failed';
      dispatch({ type: 'LOGIN_FAILURE', payload: errorMessage });
      throw error;
    }
  };

  const clearError = (): void => {
    dispatch({ type: 'CLEAR_ERROR' });
  };

  const value: AuthContextType = {
    ...state,
    login,
    register,
    logout,
    updateProfile,
    clearError,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}; 