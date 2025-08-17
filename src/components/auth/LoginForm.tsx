import { useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Loader2, Eye, EyeOff, User, Lock, Mail, Phone, MapPin } from 'lucide-react';
import { useAuth } from '@/contexts/AuthContext';
import { useLanguage } from '@/contexts/LanguageContext';
import { UserRole } from '@/types/auth';
import { useNavigate } from 'react-router-dom';

interface LoginFormProps {
  onSwitchToRegister: () => void;
}

export const LoginForm = ({ onSwitchToRegister }: LoginFormProps) => {
  const [isLogin, setIsLogin] = useState(true);
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    role: 'farmer' as UserRole,
    phone: '',
    location: '',
  });

  const { login, register, isLoading, error, clearError, isAuthenticated, user } = useAuth();
  const { t } = useLanguage();
  const navigate = useNavigate();

  // Debug authentication state
  console.log('🔍 LoginForm: Auth state:', { isAuthenticated, user, isLoading, error });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    console.log('🚀 Form submitted:', { isLogin, formData });
    clearError?.();

    if (isLogin) {
      console.log('🔐 Attempting login with:', { email: formData.email, password: formData.password });
      try {
        await login({
          email: formData.email,
          password: formData.password,
        });
        console.log('✅ Login successful, redirecting...');
        // Redirect to home page after successful login
        navigate('/');
      } catch (error) {
        console.error('❌ Login error in form:', error);
      }
    } else {
      console.log('📝 Attempting registration with:', formData);
      try {
        await register({
          name: formData.name,
          email: formData.email,
          password: formData.password,
          role: formData.role,
          phone: formData.phone,
          location: formData.location,
        });
        console.log('✅ Registration successful, redirecting...');
        // Redirect to home page after successful registration
        navigate('/');
      } catch (error) {
        console.error('❌ Registration error in form:', error);
      }
    }
  };

  const handleInputChange = (field: string, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const getRoleDescription = (role: UserRole) => {
    const descriptions = {
      farmer: 'Access farming advice, crop management, and market information',
      customer: 'Browse agricultural products and connect with farmers',
      expert: 'Provide expert advice and manage agricultural services',
    };
    return descriptions[role];
  };

  const getRoleIcon = (role: UserRole) => {
    const icons = {
      farmer: '🌾',
      customer: '🛒',
      expert: '👨‍🌾',
    };
    return icons[role];
  };

  // Use the translation function from language context
  const getText = (key: string) => {
    // Map old keys to new translation keys
    const keyMap: Record<string, string> = {
      welcomeBack: 'auth.signin',
      joinAgribot: 'auth.signup',
      signInToAccount: 'auth.signInToAccount',
      createAccount: 'auth.createAccount',
      fullName: 'auth.fullName',
      enterFullName: 'auth.fullName',
      iAmA: 'auth.role',
      selectRole: 'auth.role',
      phoneNumber: 'auth.phone',
      enterPhone: 'auth.phone',
      location: 'auth.location',
      enterLocation: 'auth.location',
      emailAddress: 'auth.email',
      enterEmail: 'auth.email',
      password: 'auth.password',
      enterPassword: 'auth.password',
      confirmPassword: 'auth.confirmPassword',
      enterConfirmPassword: 'auth.confirmPassword',
      forgotPassword: 'auth.forgotPassword',
      signIn: 'auth.signin',
      signUp: 'auth.signup',
      noAccount: 'auth.noAccount',
      haveAccount: 'auth.haveAccount',
      switchToSignUp: 'auth.signup',
      switchToSignIn: 'auth.signin',
      passwordRequirements: 'validation.passwordLength',
      passwordMismatch: 'validation.passwordMatch',
      passwordHint: 'auth.password',
      signingIn: 'auth.signin',
      createAccountButton: 'auth.signup',
      creatingAccount: 'auth.signup',
      dontHaveAccount: 'auth.noAccount',
      demoAccounts: 'common.loading',
      anyPassword: 'auth.password'
    };
    
    const translationKey = keyMap[key] || key;
    return t(translationKey);
  };

  return (
    <Card className="w-full max-w-md mx-auto p-6 space-y-6 bg-gradient-earth border-2">
      <div className="text-center space-y-2">
        <h2 className="text-2xl font-bold text-foreground">
          {isLogin ? getText('welcomeBack') : getText('joinAgribot')}
        </h2>
        <p className="text-muted-foreground">
          {isLogin ? getText('signInToAccount') : getText('createAccount')}
        </p>
      </div>

      {error && (
        <Alert variant="destructive">
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      {/* Demo Mode Indicator */}
      <Alert className="bg-blue-50 border-blue-200 text-blue-800">
        <AlertDescription>
          🧪 <strong>Demo Mode Active</strong> - Use any demo account below to test the app instantly!
        </AlertDescription>
      </Alert>

      <form onSubmit={handleSubmit} className="space-y-4">
        {!isLogin && (
          <>
            <div className="space-y-2">
              <Label htmlFor="name">{getText('fullName')}</Label>
              <div className="relative">
                <User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                <Input
                  id="name"
                  type="text"
                  placeholder={getText('enterFullName')}
                  value={formData.name}
                  onChange={(e) => handleInputChange('name', e.target.value)}
                  className="pl-10"
                  required={!isLogin}
                />
              </div>
            </div>

            <div className="space-y-2">
              <Label htmlFor="role">{getText('iAmA')}</Label>
              <Select
                value={formData.role}
                onValueChange={(value: UserRole) => handleInputChange('role', value)}
              >
                <SelectTrigger>
                  <SelectValue placeholder={getText('selectRole')} />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="farmer">
                    <div className="flex items-center gap-2">
                      <span>🌾</span>
                      <span>Farmer</span>
                    </div>
                  </SelectItem>
                  <SelectItem value="customer">
                    <div className="flex items-center gap-2">
                      <span>🛒</span>
                      <span>Customer</span>
                    </div>
                  </SelectItem>
                  <SelectItem value="expert">
                    <div className="flex items-center gap-2">
                      <span>👨‍🌾</span>
                      <span>Service Expert</span>
                    </div>
                  </SelectItem>
                </SelectContent>
              </Select>
              <p className="text-xs text-muted-foreground">
                {getRoleDescription(formData.role)}
              </p>
            </div>

            <div className="space-y-2">
              <Label htmlFor="phone">{getText('phoneNumber')}</Label>
              <div className="relative">
                <Phone className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                <Input
                  id="phone"
                  type="tel"
                  placeholder={getText('enterPhone')}
                  value={formData.phone}
                  onChange={(e) => handleInputChange('phone', e.target.value)}
                  className="pl-10"
                />
              </div>
            </div>

            <div className="space-y-2">
              <Label htmlFor="location">{getText('location')}</Label>
              <div className="relative">
                <MapPin className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                <Input
                  id="location"
                  type="text"
                  placeholder={getText('enterLocation')}
                  value={formData.location}
                  onChange={(e) => handleInputChange('location', e.target.value)}
                  className="pl-10"
                />
              </div>
            </div>
          </>
        )}

        <div className="space-y-2">
          <Label htmlFor="email">{getText('emailAddress')}</Label>
          <div className="relative">
            <Mail className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
            <Input
              id="email"
              type="email"
              placeholder={getText('enterEmail')}
              value={formData.email}
              onChange={(e) => handleInputChange('email', e.target.value)}
              className="pl-10"
              required
            />
          </div>
        </div>

        <div className="space-y-2">
          <Label htmlFor="password">{getText('password')}</Label>
          <div className="relative">
            <Lock className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
            <Input
              id="password"
              type={showPassword ? 'text' : 'password'}
              placeholder={getText('enterPassword')}
              value={formData.password}
              onChange={(e) => handleInputChange('password', e.target.value)}
              className="pl-10 pr-10"
              required
            />
            <Button
              type="button"
              variant="ghost"
              size="sm"
              className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? (
                <EyeOff className="h-4 w-4" />
              ) : (
                <Eye className="h-4 w-4" />
              )}
            </Button>
          </div>
        </div>

        <Button
          type="submit"
          className="w-full"
          disabled={isLoading}
        >
          {isLoading ? (
            <>
              <Loader2 className="mr-2 h-4 w-4 animate-spin" />
              {isLogin ? getText('signingIn') : getText('creatingAccount')}
            </>
          ) : (
            isLogin ? getText('signIn') : getText('createAccountButton')
          )}
        </Button>
      </form>

      <div className="text-center">
        <Button
          variant="link"
          onClick={() => setIsLogin(!isLogin)}
          className="text-sm"
        >
          {isLogin ? getText('dontHaveAccount') : getText('haveAccount')}
        </Button>
      </div>

      {isLogin && (
        <div className="text-center space-y-2">
          <p className="text-sm text-muted-foreground">{getText('demoAccounts')}</p>
          <div className="space-y-2">
            <Button
              variant="outline"
              size="sm"
              onClick={() => {
                console.log('🌾 Farmer demo button clicked');
                setFormData(prev => ({
                  ...prev,
                  email: 'farmer@agribot.com',
                  password: 'demo123'
                }));
                console.log('✅ Farmer demo data set');
              }}
              className="w-full text-xs"
            >
              🌾 Farmer: farmer@agribot.com / demo123
            </Button>
            <Button
              variant="outline"
              size="sm"
              onClick={() => {
                console.log('🛒 Customer demo button clicked');
                setFormData(prev => ({
                  ...prev,
                  email: 'customer@agribot.com',
                  password: 'demo123'
                }));
                console.log('✅ Customer demo data set');
              }}
              className="w-full text-xs"
            >
              🛒 Customer: customer@agribot.com / demo123
            </Button>
            <Button
              variant="outline"
              size="sm"
              onClick={() => {
                console.log('👨‍🌾 Expert demo button clicked');
                setFormData(prev => ({
                  ...prev,
                  email: 'expert@agribot.com',
                  password: 'demo123'
                }));
                console.log('✅ Expert demo data set');
              }}
              className="w-full text-xs"
            >
              👨‍🌾 Expert: expert@agribot.com / demo123
            </Button>
          </div>
        </div>
      )}
    </Card>
  );
}; 