import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { useLanguage } from '@/contexts/LanguageContext';
import { Github, Linkedin, Mail, Code, Palette, GraduationCap, BookOpen } from 'lucide-react';
import { useState, useEffect } from 'react';

interface TeamMember {
  name: string;
  role: string;
  roleLocalized: {
    en: string;
    tw: string;
    ee: string;
    ga: string;
    fa: string;
    fr: string;
  };
  description: string;
  descriptionLocalized: {
    en: string;
    tw: string;
    ee: string;
    ga: string;
    fa: string;
    fr: string;
  };
  education: string;
  educationLocalized: {
    en: string;
    tw: string;
    ee: string;
    ga: string;
    fa: string;
    fr: string;
  };
  image: string;
  skills: string[];
  social?: {
    github?: string;
    linkedin?: string;
    email?: string;
  };
}

interface TeamSectionProps {
  language?: string;
}

export const TeamSection = ({ language: propLanguage }: TeamSectionProps) => {
  const { language: contextLanguage } = useLanguage();
  const language = propLanguage || contextLanguage;
  const [isVisible, setIsVisible] = useState(false);
  const [hoveredCard, setHoveredCard] = useState<number | null>(null);

  useEffect(() => {
    // Trigger animation when component mounts
    const timer = setTimeout(() => setIsVisible(true), 100);
    return () => clearTimeout(timer);
  }, []);

  const teamMembers: TeamMember[] = [
    {
      name: "Sefakor",
      role: "Lead Developer & AI Engineer",
      roleLocalized: {
        en: "Lead Developer & AI Engineer",
        tw: "Lead Developer & AI Engineer",
        ee: "Lead Developer & AI Engineer",
        ga: "Lead Developer & AI Engineer",
        fa: "Lead Developer & AI Engineer",
        fr: "Développeur Principal & Ingénieur IA"
      },
      description: "Full-stack developer specializing in AI integration and agricultural technology solutions. Passionate about creating accessible technology for farmers.",
      descriptionLocalized: {
        en: "Full-stack developer specializing in AI integration and agricultural technology solutions. Passionate about creating accessible technology for farmers.",
        tw: "Full-stack developer a ɔyɛ AI integration ne kuayɛ mfidie nsɛm. Ɔpɛ sɛ ɔyɛ mfidie a ɛyɛ wo akuafo ma.",
        ee: "Full-stack developer si ɖe AI integration kple agblẽnɔnɔ mɔnuwo ŋu. Ƒe dɔwɔwɔ nye nu siwo wɔa dɔ agblẽnɔlawo ma.",
        ga: "Full-stack developer a ɔyɛ AI integration ne kuayɛ mfidie nsɛm. Ɔpɛ sɛ ɔyɛ mfidie a ɛyɛ wo akuafo ma.",
        fa: "Full-stack developer a ɔyɛ AI integration ne kuayɛ mfidie nsɛm. Ɔpɛ sɛ ɔyɛ mfidie a ɛyɛ wo akuafo ma.",
        fr: "Développeur full-stack spécialisé dans l'intégration IA et les solutions technologiques agricoles. Passionné par la création de technologies accessibles pour les agriculteurs."
      },
      education: "Level 400 Student at Ho Technical University",
      educationLocalized: {
        en: "Level 400 Student at Ho Technical University",
        tw: "Level 400 Student wɔ Ho Technical University",
        ee: "Level 400 Student le Ho Technical University",
        ga: "Level 400 Student wɔ Ho Technical University",
        fa: "Level 400 Student wɔ Ho Technical University",
        fr: "Étudiant de niveau 400 à l'Université Technologique de Ho"
      },
      image: "/team/sefa.jpg",
      skills: ["React", "TypeScript", "AI/ML", "Node.js", "Python"],
      social: {
        github: "https://github.com/sefa",
        linkedin: "https://linkedin.com/in/sefa",
        email: "sefa@agribot.com"
      }
    },
    {
      name: "Carlos",
      role: "UI/UX Designer & Frontend Developer",
      roleLocalized: {
        en: "UI/UX Designer & Frontend Developer",
        tw: "UI/UX Designer & Frontend Developer",
        ee: "UI/UX Designer & Frontend Developer",
        ga: "UI/UX Designer & Frontend Developer",
        fa: "UI/UX Designer & Frontend Developer",
        fr: "Designer UI/UX & Développeur Frontend"
      },
      description: "Creative designer focused on user experience and accessibility. Designs intuitive interfaces that bridge technology and traditional farming practices.",
      descriptionLocalized: {
        en: "Creative designer focused on user experience and accessibility. Designs intuitive interfaces that bridge technology and traditional farming practices.",
        tw: "Creative designer a ɔhwɛ user experience ne accessibility so. Ɔyɛ interface a ɛyɛ wo ma ɛka mfidie ne kuayɛ akwan ho.",
        ee: "Creative designer si ɖe user experience kple accessibility ŋu. Ƒe dɔwɔwɔ nye interface siwo wɔa dɔ mɔnu kple agblẽnɔnɔ ŋu.",
        ga: "Creative designer a ɔhwɛ user experience ne accessibility so. Ɔyɛ interface a ɛyɛ wo ma ɛka mfidie ne kuayɛ akwan ho.",
        fa: "Creative designer a ɔhwɛ user experience ne accessibility so. Ɔyɛ interface a ɛyɛ wo ma ɛka mfidie ne kuayɛ akwan ho.",
        fr: "Designer créatif axé sur l'expérience utilisateur et l'accessibilité. Conçoit des interfaces intuitives qui relient la technologie et les pratiques agricoles traditionnelles."
      },
      education: "Level 400 Student at Ho Technical University",
      educationLocalized: {
        en: "Level 400 Student at Ho Technical University",
        tw: "Level 400 Student wɔ Ho Technical University",
        ee: "Level 400 Student le Ho Technical University",
        ga: "Level 400 Student wɔ Ho Technical University",
        fa: "Level 400 Student wɔ Ho Technical University",
        fr: "Étudiant de niveau 400 à l'Université Technologique de Ho"
      },
      image: "/team/carlos.jpg",
      skills: ["UI/UX Design", "React", "Tailwind CSS", "Figma", "Accessibility"],
      social: {
        github: "https://github.com/carlos",
        linkedin: "https://linkedin.com/in/carlos",
        email: "carlos@agribot.com"
      }
    }
  ];

  const getLocalizedText = (texts: { en: string; tw: string; ee: string; ga: string; fa: string; fr: string }) => {
    return texts[language as keyof typeof texts] || texts.en;
  };

  return (
    <section className="py-16 bg-gradient-to-b from-background to-muted/20">
      <div className="container mx-auto px-4">
        <div className={`text-center mb-12 transition-all duration-1000 ${isVisible ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-8'}`}>
          <h2 className="text-3xl md:text-4xl font-bold text-foreground mb-4 animate-pulse">
            {language === 'en' ? 'Meet Our Team' :
             language === 'tw' ? 'Hwɛ Yɛn Fekuo' :
             language === 'ee' ? 'Kpɔ Míawo ƒe Hame' :
             language === 'ga' ? 'Hwɛ Yɛn Fekuo' :
             language === 'fa' ? 'Hwɛ Yɛn Fekuo' :
             language === 'fr' ? 'Rencontrez Notre Équipe' : 'Meet Our Team'}
          </h2>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
            {language === 'en' ? 'The passionate developers and designers behind Agribot' :
             language === 'tw' ? 'Developer ne designer a ɛyɛ Agribot no' :
             language === 'ee' ? 'Developer kple designer siwo wɔa dɔ Agribot ŋu' :
             language === 'ga' ? 'Developer ne designer a ɛyɛ Agribot no' :
             language === 'fa' ? 'Developer ne designer a ɛyɛ Agribot no' :
             language === 'fr' ? 'Les développeurs et designers passionnés derrière Agribot' : 'The passionate developers and designers behind Agribot'}
          </p>
        </div>

        <div className="grid md:grid-cols-2 gap-8 max-w-4xl mx-auto">
          {teamMembers.map((member, index) => (
            <Card 
              key={index} 
              className={`p-6 transition-all duration-500 border-2 transform hover:scale-105 hover:shadow-xl ${
                hoveredCard === index ? 'border-primary shadow-lg' : 'border-border hover:border-primary/50'
              } ${isVisible ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-8'}`}
              style={{ 
                animationDelay: `${index * 200}ms`,
                transitionDelay: `${index * 200}ms`
              }}
              onMouseEnter={() => setHoveredCard(index)}
              onMouseLeave={() => setHoveredCard(null)}
            >
              <div className="flex flex-col items-center text-center space-y-4">
                {/* Profile Image with Animation */}
                <div className="relative group">
                  <div className="w-32 h-32 rounded-full overflow-hidden border-4 border-primary/20 bg-gradient-primary transition-all duration-300 group-hover:border-primary group-hover:scale-110">
                    <img
                      src={member.image}
                      alt={member.name}
                      className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
                      onError={(e) => {
                        // Fallback to placeholder if image fails to load
                        const target = e.target as HTMLImageElement;
                        target.src = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 128 128'%3E%3Crect width='128' height='128' fill='%23e5e7eb'/%3E%3Ctext x='64' y='64' text-anchor='middle' dy='.3em' font-family='Arial' font-size='48' fill='%239ca3af'%3E" + member.name.charAt(0) + "%3C/text%3E%3C/svg%3E";
                      }}
                    />
                  </div>
                  <div className="absolute -bottom-2 -right-2 p-2 bg-primary rounded-full transition-all duration-300 group-hover:scale-110 group-hover:rotate-12">
                    {member.name === "Sefakor" ? (
                      <Code className="h-4 w-4 text-primary-foreground" />
                    ) : (
                      <Palette className="h-4 w-4 text-primary-foreground" />
                    )}
                  </div>
                </div>

                {/* Member Info with Animations */}
                <div className="space-y-2">
                  <h3 className="text-xl font-bold text-foreground transition-colors duration-300 group-hover:text-primary">
                    {member.name}
                  </h3>
                  <Badge variant="secondary" className="text-sm transition-all duration-300 hover:bg-primary hover:text-primary-foreground">
                    {getLocalizedText(member.roleLocalized)}
                  </Badge>
                  
                  {/* Education Badge */}
                  <div className="flex items-center justify-center gap-2 mt-2">
                    <GraduationCap className="h-4 w-4 text-primary" />
                    <Badge variant="outline" className="text-xs bg-primary/10 border-primary/30">
                      {getLocalizedText(member.educationLocalized)}
                    </Badge>
                  </div>
                  
                  <p className="text-sm text-muted-foreground leading-relaxed">
                    {getLocalizedText(member.descriptionLocalized)}
                  </p>
                </div>

                {/* Skills with Hover Effects */}
                <div className="flex flex-wrap justify-center gap-2">
                  {member.skills.map((skill, skillIndex) => (
                    <Badge 
                      key={skillIndex} 
                      variant="outline" 
                      className="text-xs transition-all duration-300 hover:bg-primary hover:text-primary-foreground hover:scale-105 cursor-pointer"
                      style={{ animationDelay: `${skillIndex * 100}ms` }}
                    >
                      {skill}
                    </Badge>
                  ))}
                </div>

                {/* Social Links with Animations */}
                {member.social && (
                  <div className="flex gap-3 pt-2">
                    {member.social.github && (
                      <a
                        href={member.social.github}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="p-2 rounded-full bg-muted hover:bg-primary hover:text-primary-foreground transition-all duration-300 hover:scale-110 hover:rotate-12"
                      >
                        <Github className="h-4 w-4" />
                      </a>
                    )}
                    {member.social.linkedin && (
                      <a
                        href={member.social.linkedin}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="p-2 rounded-full bg-muted hover:bg-primary hover:text-primary-foreground transition-all duration-300 hover:scale-110 hover:rotate-12"
                      >
                        <Linkedin className="h-4 w-4" />
                      </a>
                    )}
                    {member.social.email && (
                      <a
                        href={`mailto:${member.social.email}`}
                        className="p-2 rounded-full bg-muted hover:bg-primary hover:text-primary-foreground transition-all duration-300 hover:scale-110 hover:rotate-12"
                      >
                        <Mail className="h-4 w-4" />
                      </a>
                    )}
                  </div>
                )}
              </div>
            </Card>
          ))}
        </div>

        {/* Team Mission Statement with Animation */}
        <div className={`text-center mt-12 transition-all duration-1000 ${isVisible ? 'opacity-100 translate-y-0' : 'opacity-0 translate-y-8'}`} style={{ animationDelay: '600ms' }}>
          <Card className="p-6 bg-gradient-primary border-2 max-w-3xl mx-auto hover:shadow-xl transition-all duration-300 hover:scale-105">
            <div className="text-primary-foreground">
              <h3 className="text-xl font-bold mb-3 flex items-center justify-center gap-2">
                <BookOpen className="h-5 w-5 animate-pulse" />
                {language === 'en' ? 'Our Mission' :
                 language === 'tw' ? 'Yɛn Adwuma' :
                 language === 'ee' ? 'Míawo ƒe Dɔwɔwɔ' :
                 language === 'ga' ? 'Yɛn Adwuma' :
                 language === 'fa' ? 'Yɛn Adwuma' :
                 language === 'fr' ? 'Notre Mission' : 'Our Mission'}
              </h3>
              <p className="text-primary-foreground/90 leading-relaxed">
                {language === 'en' ? 'As Level 400 students at Ho Technical University, we are passionate about leveraging technology to solve real-world agricultural challenges in Ghana. Our mission is to bridge the communication gap between farmers, extension officers, and customers through innovative multilingual solutions.' :
                 language === 'tw' ? 'Sɛ Level 400 students wɔ Ho Technical University, yɛpɛ sɛ yɛfa mfidie ma yɛyɛ kuayɛ nsɛm a ɛhaw Ghana. Yɛn adwuma nye sɛ yɛka akuafo, extension officers, ne customers ho yɛ innovative multilingual solutions.' :
                 language === 'ee' ? 'Esi míawo nye Level 400 students le Ho Technical University, míawo lɔ̃ nu siwo wɔa dɔ mɔnu ma míawoa ɖe agblẽnɔnɔ ƒe dɔwɔwɔ le Ghana. Míawo ƒe dɔwɔwɔ nye nu siwo wɔa dɔ agblẽnɔlawo, extension officers, kple asiwo ƒe dɔwɔwɔ ŋu.' :
                 language === 'ga' ? 'Sɛ Level 400 students wɔ Ho Technical University, yɛpɛ sɛ yɛfa mfidie ma yɛyɛ kuayɛ nsɛm a ɛhaw Ghana. Yɛn adwuma nye sɛ yɛka akuafo, extension officers, ne customers ho yɛ innovative multilingual solutions.' :
                 language === 'fa' ? 'Sɛ Level 400 students wɔ Ho Technical University, yɛpɛ sɛ yɛfa mfidie ma yɛyɛ kuayɛ nsɛm a ɛhaw Ghana. Yɛn adwuma nye sɛ yɛka akuafo, extension officers, ne customers ho yɛ innovative multilingual solutions.' :
                 language === 'fr' ? 'Comme étudiants de niveau 400 à l\'Université Technologique de Ho, nous sommes passionnés par l\'exploitation de la technologie pour résoudre les défis agricoles réels au Ghana. Notre mission est de combler la lacune de communication entre les agriculteurs, les agents d\'extension et les clients grâce à des solutions multilingues innovantes.' : 'Our mission statement'}
              </p>
            </div>
          </Card>
        </div>
      </div>
    </section>
  );
}; 