import { supabase } from '@/lib/supabase';
import { LoginCredentials, RegisterData, User } from '@/types/auth';

// Mock users for immediate testing
const MOCK_USERS: User[] = [
  {
    id: 'mock-farmer-1',
    name: 'Demo Farmer',
    email: 'farmer@agribot.com',
    role: 'farmer',
    phone: '+233 24 123 4567',
    location: 'Kumasi, Ghana',
    avatar: undefined,
    createdAt: new Date(),
    lastLogin: new Date(),
  },
  {
    id: 'mock-customer-1',
    name: 'Demo Customer',
    email: 'customer@agribot.com',
    role: 'customer',
    phone: '+233 24 123 4568',
    location: 'Accra, Ghana',
    avatar: undefined,
    createdAt: new Date(),
    lastLogin: new Date(),
  },
  {
    id: 'mock-expert-1',
    name: 'Demo Expert',
    email: 'expert@agribot.com',
    role: 'expert',
    phone: '+233 24 123 4569',
    location: 'Tamale, Ghana',
    avatar: undefined,
    createdAt: new Date(),
    lastLogin: new Date(),
  },
];

export class AuthService {
  // Mock authentication for immediate testing
  private useMockAuth = true; // Set to false to use real Supabase

  constructor() {
    if (this.useMockAuth) {
      console.log('🔧 Using Mock Authentication - Demo Mode Active');
      console.log('📧 Demo Accounts:');
      console.log('   🌾 Farmer: farmer@agribot.com / demo123');
      console.log('   🛒 Customer: customer@agribot.com / demo123');
      console.log('   👨‍🌾 Expert: expert@agribot.com / demo123');
    }
  }

  // Sign up with email and password
  async signUp(data: RegisterData): Promise<{ user: User | null; error: string | null }> {
    if (this.useMockAuth) {
      // Mock registration
      await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate delay
      
      const mockUser: User = {
        id: `mock-${data.role}-${Date.now()}`,
        name: data.name,
        email: data.email,
        role: data.role,
        phone: data.phone || '',
        location: data.location || '',
        avatar: undefined,
        createdAt: new Date(),
        lastLogin: new Date(),
      };
      
      return { user: mockUser, error: null };
    }

    try {
      const { data: authData, error } = await supabase.auth.signUp({
        email: data.email,
        password: data.password,
        options: {
          data: {
            full_name: data.name,
            role: data.role,
            phone: data.phone,
            location: data.location,
          }
        }
      });

      if (error) {
        return { user: null, error: error.message };
      }

      if (authData.user) {
        const user: User = {
          id: authData.user.id,
          name: data.name,
          email: data.email,
          role: data.role,
          phone: data.phone || '',
          location: data.location || '',
          avatar: undefined,
          createdAt: new Date(),
          lastLogin: new Date(),
        };

        return { user, error: null };
      }

      return { user: null, error: 'Sign up failed' };
    } catch (error) {
      return { user: null, error: 'An unexpected error occurred' };
    }
  }

  // Sign in with email and password
  async signIn(credentials: LoginCredentials): Promise<{ user: User | null; error: string | null }> {
    if (this.useMockAuth) {
      // Mock authentication
      await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate delay
      
      const mockUser = MOCK_USERS.find(user => 
        user.email === credentials.email && credentials.password === 'demo123'
      );
      
      if (mockUser) {
        return { user: mockUser, error: null };
      } else {
        return { user: null, error: 'Invalid email or password. Use demo123 for all demo accounts.' };
      }
    }

    try {
      const { data: authData, error } = await supabase.auth.signInWithPassword({
        email: credentials.email,
        password: credentials.password,
      });

      if (error) {
        return { user: null, error: error.message };
      }

      if (authData.user) {
        // Get user profile from Supabase
        const { data: profile, error: profileError } = await supabase
          .from('users')
          .select('*')
          .eq('id', authData.user.id)
          .single();

        if (profileError && profileError.code !== 'PGRST116') {
          // Create user profile if it doesn't exist
          const userData = {
            id: authData.user.id,
            email: authData.user.email!,
            full_name: authData.user.user_metadata?.full_name || 'User',
            role: authData.user.user_metadata?.role || 'farmer',
            phone: authData.user.user_metadata?.phone || '',
            location: authData.user.user_metadata?.location || '',
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString(),
          };

          await supabase.from('users').insert(userData);
        }

        const user: User = {
          id: authData.user.id,
          name: authData.user.user_metadata?.full_name || profile?.full_name || 'User',
          email: authData.user.email!,
          role: authData.user.user_metadata?.role || profile?.role || 'farmer',
          phone: authData.user.user_metadata?.phone || profile?.phone || '',
          location: authData.user.user_metadata?.location || profile?.location || '',
          avatar: undefined,
          createdAt: new Date(profile?.created_at || Date.now()),
          lastLogin: new Date(),
        };

        return { user, error: null };
      }

      return { user: null, error: 'Sign in failed' };
    } catch (error) {
      return { user: null, error: 'An unexpected error occurred' };
    }
  }

  // Sign in with Google OAuth
  async signInWithGoogle(): Promise<{ error: string | null }> {
    if (this.useMockAuth) {
      return { error: 'Google OAuth not available in demo mode' };
    }

    try {
      const { error } = await supabase.auth.signInWithOAuth({
        provider: 'google',
        options: {
          redirectTo: `${window.location.origin}/auth/callback`
        }
      });

      if (error) {
        return { error: error.message };
      }

      return { error: null };
    } catch (error) {
      return { error: 'An unexpected error occurred' };
    }
  }

  // Sign out
  async signOut(): Promise<{ error: string | null }> {
    if (this.useMockAuth) {
      // Mock signout
      await new Promise(resolve => setTimeout(resolve, 500)); // Simulate delay
      return { error: null };
    }

    try {
      const { error } = await supabase.auth.signOut();
      
      if (error) {
        return { error: error.message };
      }

      return { error: null };
    } catch (error) {
      return { error: 'An unexpected error occurred' };
    }
  }

  // Get current user
  async getCurrentUser(): Promise<User | null> {
    if (this.useMockAuth) {
      // Return null for mock auth (user needs to sign in)
      return null;
    }

    try {
      const { data: { user }, error } = await supabase.auth.getUser();

      if (error || !user) {
        return null;
      }

      // Get user profile
      const { data: profile } = await supabase
        .from('users')
        .select('*')
        .eq('id', user.id)
        .single();

      if (profile) {
        return {
          id: user.id,
          name: profile.full_name,
          email: user.email!,
          role: profile.role,
          phone: profile.phone || '',
          location: profile.location || '',
          avatar: undefined,
          createdAt: new Date(profile.created_at),
          lastLogin: new Date(),
        };
      }

      return null;
    } catch (error) {
      return null;
    }
  }

  // Update user profile
  async updateProfile(updates: Partial<User>): Promise<{ user: User | null; error: string | null }> {
    if (this.useMockAuth) {
      // Mock profile update
      await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate delay
      
      const mockUser: User = {
        id: 'mock-user-1',
        name: updates.name || 'Demo User',
        email: updates.email || 'demo@agribot.com',
        role: updates.role || 'farmer',
        phone: updates.phone || '',
        location: updates.location || '',
        avatar: undefined,
        createdAt: new Date(),
        lastLogin: new Date(),
      };
      
      return { user: mockUser, error: null };
    }

    try {
      const { data: { user } } = await supabase.auth.getUser();
      
      if (!user) {
        return { user: null, error: 'User not authenticated' };
      }

      const updateData = {
        full_name: updates.name,
        role: updates.role,
        phone: updates.phone,
        location: updates.location,
        updated_at: new Date().toISOString(),
      };

      const { data: profile, error } = await supabase
        .from('users')
        .update(updateData)
        .eq('id', user.id)
        .select()
        .single();

      if (error) {
        return { user: null, error: error.message };
      }

      const updatedUser: User = {
        id: user.id,
        name: profile.full_name,
        email: user.email!,
        role: profile.role,
        phone: profile.phone || '',
        location: profile.location || '',
        avatar: undefined,
        createdAt: new Date(profile.created_at),
        lastLogin: new Date(),
      };

      return { user: updatedUser, error: null };
    } catch (error) {
      return { user: null, error: 'An unexpected error occurred' };
    }
  }

  // Reset password
  async resetPassword(email: string): Promise<{ error: string | null }> {
    if (this.useMockAuth) {
      return { error: 'Password reset not available in demo mode' };
    }

    try {
      const { error } = await supabase.auth.resetPasswordForEmail(email, {
        redirectTo: `${window.location.origin}/reset-password`,
      });

      if (error) {
        return { error: error.message };
      }

      return { error: null };
    } catch (error) {
      return { error: 'An unexpected error occurred' };
    }
  }

  // Check if user is authenticated
  async isAuthenticated(): Promise<boolean> {
    if (this.useMockAuth) {
      // Mock authentication check
      return false; // Always false in mock mode
    }

    const { data: { user } } = await supabase.auth.getUser();
    return !!user;
  }

  // Get auth session
  async getSession() {
    if (this.useMockAuth) {
      return { session: null, error: null };
    }

    const { data: { session }, error } = await supabase.auth.getSession();
    return { session, error };
  }

  // Listen to auth state changes
  onAuthStateChange(callback: (event: string, session: { user?: { email?: string } } | null) => void) {
    if (this.useMockAuth) {
      // Mock auth state change listener
      return {
        data: {
          subscription: {
            unsubscribe: () => {}
          }
        }
      };
    }

    return supabase.auth.onAuthStateChange(callback);
  }
}

// Export singleton instance
export const authService = new AuthService(); 