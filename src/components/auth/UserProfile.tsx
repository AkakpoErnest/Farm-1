import { useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import { 
  User, 
  Mail, 
  Phone, 
  MapPin, 
  Calendar, 
  LogOut, 
  Edit, 
  Save, 
  X,
  Shield,
  Star
} from 'lucide-react';
import { useAuth } from '@/contexts/AuthContext';
import { UserRole } from '@/types/auth';

export const UserProfile = () => {
  const { user, logout, updateProfile } = useAuth();
  const [isEditing, setIsEditing] = useState(false);
  const [editData, setEditData] = useState({
    name: user?.name || '',
    phone: user?.phone || '',
    location: user?.location || '',
  });

  if (!user) return null;

  const handleSave = async () => {
    await updateProfile(editData);
    setIsEditing(false);
  };

  const handleCancel = () => {
    setEditData({
      name: user.name,
      phone: user.phone || '',
      location: user.location || '',
    });
    setIsEditing(false);
  };

  const getRoleInfo = (role: UserRole) => {
    const roleInfo = {
      farmer: {
        icon: '🌾',
        title: 'Farmer',
        description: 'Agricultural producer',
        color: 'bg-green-100 text-green-800',
        features: ['Crop management', 'Market access', 'Weather alerts', 'Expert advice']
      },
      customer: {
        icon: '🛒',
        title: 'Customer',
        description: 'Agricultural product buyer',
        color: 'bg-blue-100 text-blue-800',
        features: ['Product browsing', 'Farmer connections', 'Quality assurance', 'Delivery tracking']
      },
      expert: {
        icon: '👨‍🌾',
        title: 'Service Expert',
        description: 'Agricultural specialist',
        color: 'bg-purple-100 text-purple-800',
        features: ['Expert consultations', 'Service management', 'Knowledge sharing', 'Quality control']
      }
    };
    return roleInfo[role];
  };

  const roleInfo = getRoleInfo(user.role);

  return (
    <Card className="w-full max-w-2xl mx-auto p-6 space-y-6 bg-gradient-earth border-2">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-4">
          <Avatar className="h-16 w-16">
            <AvatarImage src={user.avatar} alt={user.name} />
            <AvatarFallback className="text-lg">
              {user.name.split(' ').map(n => n[0]).join('')}
            </AvatarFallback>
          </Avatar>
          <div>
            <h2 className="text-2xl font-bold text-foreground">{user.name}</h2>
            <div className="flex items-center gap-2">
              <Badge className={roleInfo.color}>
                {roleInfo.icon} {roleInfo.title}
              </Badge>
              <span className="text-sm text-muted-foreground">
                Member since {new Date(user.createdAt).toLocaleDateString()}
              </span>
            </div>
          </div>
        </div>
        <Button
          variant="outline"
          size="sm"
          onClick={logout}
          className="gap-2"
        >
          <LogOut className="h-4 w-4" />
          Logout
        </Button>
      </div>

      <Separator />

      {/* Profile Information */}
      <div className="space-y-4">
        <div className="flex items-center justify-between">
          <h3 className="text-lg font-semibold">Profile Information</h3>
          <Button
            variant="outline"
            size="sm"
            onClick={() => setIsEditing(!isEditing)}
            className="gap-2"
          >
            {isEditing ? <X className="h-4 w-4" /> : <Edit className="h-4 w-4" />}
            {isEditing ? 'Cancel' : 'Edit'}
          </Button>
        </div>

        <div className="grid gap-4">
          <div className="flex items-center gap-3">
            <Mail className="h-4 w-4 text-muted-foreground" />
            <div className="flex-1">
              <Label className="text-sm font-medium">Email</Label>
              <p className="text-sm text-muted-foreground">{user.email}</p>
            </div>
          </div>

          <div className="flex items-center gap-3">
            <User className="h-4 w-4 text-muted-foreground" />
            <div className="flex-1">
              <Label className="text-sm font-medium">Full Name</Label>
              {isEditing ? (
                <Input
                  value={editData.name}
                  onChange={(e) => setEditData(prev => ({ ...prev, name: e.target.value }))}
                  className="mt-1"
                />
              ) : (
                <p className="text-sm text-muted-foreground">{user.name}</p>
              )}
            </div>
          </div>

          <div className="flex items-center gap-3">
            <Phone className="h-4 w-4 text-muted-foreground" />
            <div className="flex-1">
              <Label className="text-sm font-medium">Phone Number</Label>
              {isEditing ? (
                <Input
                  value={editData.phone}
                  onChange={(e) => setEditData(prev => ({ ...prev, phone: e.target.value }))}
                  placeholder="+233 XX XXX XXXX"
                  className="mt-1"
                />
              ) : (
                <p className="text-sm text-muted-foreground">
                  {user.phone || 'Not provided'}
                </p>
              )}
            </div>
          </div>

          <div className="flex items-center gap-3">
            <MapPin className="h-4 w-4 text-muted-foreground" />
            <div className="flex-1">
              <Label className="text-sm font-medium">Location</Label>
              {isEditing ? (
                <Input
                  value={editData.location}
                  onChange={(e) => setEditData(prev => ({ ...prev, location: e.target.value }))}
                  placeholder="City, Region"
                  className="mt-1"
                />
              ) : (
                <p className="text-sm text-muted-foreground">
                  {user.location || 'Not provided'}
                </p>
              )}
            </div>
          </div>

          <div className="flex items-center gap-3">
            <Calendar className="h-4 w-4 text-muted-foreground" />
            <div className="flex-1">
              <Label className="text-sm font-medium">Last Login</Label>
              <p className="text-sm text-muted-foreground">
                {new Date(user.lastLogin).toLocaleString()}
              </p>
            </div>
          </div>
        </div>

        {isEditing && (
          <div className="flex gap-2 pt-4">
            <Button onClick={handleSave} className="gap-2">
              <Save className="h-4 w-4" />
              Save Changes
            </Button>
            <Button variant="outline" onClick={handleCancel} className="gap-2">
              <X className="h-4 w-4" />
              Cancel
            </Button>
          </div>
        )}
      </div>

      <Separator />

      {/* Role Features */}
      <div className="space-y-4">
        <div className="flex items-center gap-2">
          <Shield className="h-5 w-5 text-primary" />
          <h3 className="text-lg font-semibold">Your {roleInfo.title} Features</h3>
        </div>
        
        <div className="grid gap-2">
          {roleInfo.features.map((feature, index) => (
            <div key={index} className="flex items-center gap-2">
              <Star className="h-4 w-4 text-yellow-500" />
              <span className="text-sm">{feature}</span>
            </div>
          ))}
        </div>
      </div>

      <Separator />

      {/* Quick Actions */}
      <div className="space-y-4">
        <h3 className="text-lg font-semibold">Quick Actions</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
          <Button variant="outline" className="justify-start gap-2">
            <User className="h-4 w-4" />
            View Dashboard
          </Button>
          <Button variant="outline" className="justify-start gap-2">
            <Mail className="h-4 w-4" />
            Messages
          </Button>
          <Button variant="outline" className="justify-start gap-2">
            <MapPin className="h-4 w-4" />
            My Location
          </Button>
          <Button variant="outline" className="justify-start gap-2">
            <Shield className="h-4 w-4" />
            Settings
          </Button>
        </div>
      </div>
    </Card>
  );
}; 