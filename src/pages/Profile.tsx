import { UserProfile } from "@/components/auth/UserProfile";
import { Button } from "@/components/ui/button";
import { ArrowLeft } from 'lucide-react';
import { Link } from 'react-router-dom';
import agribotLogo from "/agribot-logo.png";

const Profile = () => {
  return (
    <div className="min-h-screen bg-gradient-earth">
      {/* Header */}
      <div className="bg-gradient-primary text-primary-foreground py-4">
        <div className="container mx-auto px-4">
          <div className="flex items-center justify-between">
            <Link to="/">
              <Button variant="ghost" size="sm" className="gap-2 text-primary-foreground hover:bg-primary-foreground/20">
                <ArrowLeft className="h-4 w-4" />
                Back to Home
              </Button>
            </Link>
            <div className="flex items-center gap-3">
              <img src={agribotLogo} alt="Agribot Logo" className="h-8 w-auto bg-white/10 rounded-lg p-1" />
              <h1 className="text-xl font-semibold">My Profile</h1>
            </div>
            <div className="w-20"></div> {/* Spacer for centering */}
          </div>
        </div>
      </div>

      {/* Profile Content */}
      <div className="container mx-auto px-4 py-8">
        <UserProfile />
      </div>
    </div>
  );
};

export default Profile; 