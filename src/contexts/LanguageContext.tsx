import React, { createContext, useContext, useState, useEffect } from 'react';

export type Language = 'en' | 'tw' | 'ee' | 'ga' | 'fa' | 'fr';

export interface LanguageContextType {
  language: Language;
  setLanguage: (lang: Language) => void;
  t: (key: string) => string;
}

const LanguageContext = createContext<LanguageContextType | undefined>(undefined);

// Translation keys for the entire app
const translations = {
  en: {
    // Navigation
    'nav.home': 'Home',
    'nav.features': 'Features',
    'nav.login': 'Login',
    'nav.signup': 'Sign Up',
    'nav.profile': 'Profile',
    'nav.logout': 'Logout',

    // Authentication
    'auth.signin': 'Sign In',
    'auth.signup': 'Sign Up',
    'auth.email': 'Email',
    'auth.password': 'Password',
    'auth.confirmPassword': 'Confirm Password',
    'auth.fullName': 'Full Name',
    'auth.phone': 'Phone Number',
    'auth.location': 'Location',
    'auth.role': 'Role',
    'auth.role.farmer': 'Farmer',
    'auth.role.expert': 'Expert',
    'auth.role.customer': 'Customer',
    'auth.submit': 'Submit',
    'auth.cancel': 'Cancel',
    'auth.forgotPassword': 'Forgot Password?',
    'auth.resetPassword': 'Reset Password',
    'auth.noAccount': "Don't have an account?",
    'auth.haveAccount': 'Already have an account?',
    'auth.createAccount': 'Create Account',
    'auth.signInToAccount': 'Sign in to your account',

    // Form validation
    'validation.required': 'This field is required',
    'validation.email': 'Please enter a valid email',
    'validation.passwordLength': 'Password must be at least 6 characters',
    'validation.passwordMatch': 'Passwords do not match',
    'validation.phone': 'Please enter a valid phone number',

    // Common
    'common.loading': 'Loading...',
    'common.error': 'Error',
    'common.success': 'Success',
    'common.welcome': 'Welcome',
    'common.hello': 'Hello',
    'common.yes': 'Yes',
    'common.no': 'No',
    'common.save': 'Save',
    'common.edit': 'Edit',
    'common.delete': 'Delete',
    'common.close': 'Close',
    'common.back': 'Back',
    'common.next': 'Next',
    'common.previous': 'Previous',

    // Home page
    'home.hero.title': 'Agricultural AI Assistant for Ghana',
    'home.hero.subtitle': 'Bridging Communication in Ghana\'s Agriculture through multilingual AI assistance',
    'home.hero.description': 'Get expert farming advice, weather updates, market prices, and more in your preferred language',
    'home.hero.getStarted': 'Get Started',
    'home.hero.learnMore': 'Learn More',

    // Features
    'features.title': 'Features',
    'features.chat.title': 'AI Chat Assistant',
    'features.chat.description': 'Get instant farming advice in your language',
    'features.weather.title': 'Weather Updates',
    'features.weather.description': 'Real-time weather information for your location',
    'features.market.title': 'Market Prices',
    'features.market.description': 'Stay updated with current crop prices',
    'features.subsidies.title': 'Agricultural Subsidies',
    'features.subsidies.description': 'Information about available government support',

    // Language selector
    'language.title': 'Choose Your Language',
    'language.english': 'English',
    'language.twi': 'Twi',
    'language.ewe': 'Ewe',
    'language.ga': 'Ga',
    'language.fante': 'Fante',
    'language.french': 'French',

    // Profile
    'profile.title': 'User Profile',
    'profile.edit': 'Edit Profile',
    'profile.personalInfo': 'Personal Information',
    'profile.accountInfo': 'Account Information',
    'profile.updateSuccess': 'Profile updated successfully',
    'profile.updateError': 'Failed to update profile',

    // Chat
    'chat.title': 'AI Chat Assistant',
    'chat.placeholder': 'Ask me anything about farming...',
    'chat.send': 'Send',
    'chat.clear': 'Clear Chat',
    'chat.loading': 'AI is thinking...',

    // Weather
    'weather.title': 'Weather Information',
    'weather.current': 'Current Weather',
    'weather.forecast': 'Forecast',
    'weather.temperature': 'Temperature',
    'weather.humidity': 'Humidity',
    'weather.description': 'Description',

    // Market
    'market.title': 'Market Prices',
    'market.crop': 'Crop',
    'market.price': 'Price',
    'market.location': 'Location',
    'market.lastUpdated': 'Last Updated',

    // Subsidies
    'subsidies.title': 'Agricultural Subsidies',
    'subsidies.available': 'Available Programs',
    'subsidies.amount': 'Amount',
    'subsidies.deadline': 'Application Deadline',
    'subsidies.eligibility': 'Eligibility Criteria',

    // Team
    'team.title': 'Our Team',
    'team.description': 'Meet the developers behind Farm Talk Ghana',
    'team.carlos.title': 'Carlos',
    'team.carlos.role': 'Full Stack Developer',
    'team.sefa.title': 'Sefakor',
    'team.sefa.role': 'Frontend Developer',

    // Footer
    'footer.copyright': '© 2024 Farm Talk Ghana. All rights reserved.',
    'footer.developed': 'Developed by Level 400 Students, Ho Technical University',
  },

  tw: {
    // Navigation
    'nav.home': 'Fie',
    'nav.features': 'Nneɛma',
    'nav.login': 'Bra Mu',
    'nav.signup': 'Fie Kyerɛw',
    'nav.profile': 'Wo Ho Nsɛm',
    'nav.logout': 'Pue',

    // Authentication
    'auth.signin': 'Bra Mu',
    'auth.signup': 'Fie Kyerɛw',
    'auth.email': 'Email',
    'auth.password': 'Password',
    'auth.confirmPassword': 'San Password No',
    'auth.fullName': 'Wo Din Nyinaa',
    'auth.phone': 'Wo Phone Number',
    'auth.location': 'Bea',
    'auth.role': 'Wo Role',
    'auth.role.farmer': 'Kuayefo',
    'auth.role.expert': 'Nimdefo',
    'auth.role.customer': 'Tɔnfo',
    'auth.submit': 'Fa',
    'auth.cancel': 'Gyae',
    'auth.forgotPassword': 'Wo Wɔ Password?',
    'auth.resetPassword': 'San Password No',
    'auth.noAccount': 'Wo Nni Account?',
    'auth.haveAccount': 'Wo Wɔ Account?',
    'auth.createAccount': 'Yɛ Account',
    'auth.signInToAccount': 'Bra wo account mu',

    // Form validation
    'validation.required': 'Yɛ wo sɛ wo de biribi fa',
    'validation.email': 'Fa email a ɛyɛ yie',
    'validation.passwordLength': 'Password nso yɛ mma 6',
    'validation.passwordMatch': 'Password no nso yɛ yie',
    'validation.phone': 'Fa phone number a ɛyɛ yie',

    // Common
    'common.loading': 'Reyɛ...',
    'common.error': 'Nsɛm',
    'common.success': 'Yɛɛ yie',
    'common.welcome': 'Akwaaba',
    'common.hello': 'Hɛlo',
    'common.yes': 'Aane',
    'common.no': 'Daabi',
    'common.save': 'Fa',
    'common.edit': 'Sesa',
    'common.delete': 'Sɛɛ',
    'common.close': 'To',
    'common.back': 'San',
    'common.next': 'Akyiri',
    'common.previous': 'Akan',

    // Home page
    'home.hero.title': 'Ghana Kuayɛ AI Boa',
    'home.hero.subtitle': 'Yɛ Kuayɛ Nsɛm Wɔ Ghana Mu Wɔ Kasa Nyinaa Mu',
    'home.hero.description': 'Fa kuayɛ nimdeɛ, weather nsɛm, market bo, ne nneɛma foforɔ wɔ wo kasa mu',
    'home.hero.getStarted': 'Fie Yɛ',
    'home.hero.learnMore': 'Fa Nimdeɛ',

    // Features
    'features.title': 'Nneɛma',
    'features.chat.title': 'AI Chat Boa',
    'features.chat.description': 'Fa kuayɛ nimdeɛ wɔ wo kasa mu',
    'features.weather.title': 'Weather Nsɛm',
    'features.weather.description': 'Weather nsɛm wɔ wo bea mu',
    'features.market.title': 'Market Bo',
    'features.market.description': 'Fa crop bo a ɛwɔ sɛ',
    'features.subsidies.title': 'Kuayɛ Boa',
    'features.subsidies.description': 'Nsɛm wɔ government boa a ɛwɔ',

    // Language selector
    'language.title': 'Paw Wo Kasa',
    'language.english': 'English',
    'language.twi': 'Twi',
    'language.ewe': 'Ewe',
    'language.ga': 'Ga',
    'language.fante': 'Fante',
    'language.french': 'French',

    // Profile
    'profile.title': 'Wo Ho Nsɛm',
    'profile.edit': 'Sesa Wo Ho Nsɛm',
    'profile.personalInfo': 'Wo Ho Nsɛm',
    'profile.accountInfo': 'Account Nsɛm',
    'profile.updateSuccess': 'Wo ho nsɛm yɛɛ yie',
    'profile.updateError': 'Wo ho nsɛm nso yɛɛ yie',

    // Chat
    'chat.title': 'AI Chat Boa',
    'chat.placeholder': 'Bisa me biribi wɔ kuayɛ ho...',
    'chat.send': 'Fa',
    'chat.clear': 'Sɛɛ Chat',
    'chat.loading': 'AI reyɛ adwene...',

    // Weather
    'weather.title': 'Weather Nsɛm',
    'weather.current': 'Weather a ɛwɔ sɛ',
    'weather.forecast': 'Weather a ɛbɛba',
    'weather.temperature': 'Temperatur',
    'weather.humidity': 'Humidity',
    'weather.description': 'Nkyerɛ',

    // Market
    'market.title': 'Market Bo',
    'market.crop': 'Crop',
    'market.price': 'Bo',
    'market.location': 'Bea',
    'market.lastUpdated': 'Sɛɛɛ',

    // Subsidies
    'subsidies.title': 'Kuayɛ Boa',
    'subsidies.available': 'Program a ɛwɔ',
    'subsidies.amount': 'Bo',
    'subsidies.deadline': 'Application Deadline',
    'subsidies.eligibility': 'Eligibility Criteria',

    // Team
    'team.title': 'Yɛn Team',
    'team.description': 'Fa developers a ɛyɛɛ Farm Talk Ghana',
    'team.carlos.title': 'Carlos',
    'team.carlos.role': 'Full Stack Developer',
    'team.sefa.title': 'Sefakor',
    'team.sefa.role': 'Frontend Developer',

    // Footer
    'footer.copyright': '© 2024 Farm Talk Ghana. Yɛn rights nyinaa.',
    'footer.developed': 'Yɛɛɛ wɔ Level 400 Students, Ho Technical University',
  },

  ee: {
    // Navigation
    'nav.home': 'Aƒe',
    'nav.features': 'Nuwɔwɔ',
    'nav.login': 'Trɔ Kpe',
    'nav.signup': 'Ŋlɔ Kpe',
    'nav.profile': 'Wò Nyawo',
    'nav.logout': 'Vɔ',

    // Authentication
    'auth.signin': 'Trɔ Kpe',
    'auth.signup': 'Ŋlɔ Kpe',
    'auth.email': 'Email',
    'auth.password': 'Gbeɖoɖo',
    'auth.confirmPassword': 'Gbeɖoɖo Gbɔ',
    'auth.fullName': 'Wò Ŋkɔ Nyui',
    'auth.phone': 'Wò Telefon',
    'auth.location': 'Be',
    'auth.role': 'Wò Dɔ',
    'auth.role.farmer': 'Agbledela',
    'auth.role.expert': 'Gbetɔ',
    'auth.role.customer': 'Dɔla',
    'auth.submit': 'Ɖo',
    'auth.cancel': 'Tso',
    'auth.forgotPassword': 'Wò Gbeɖoɖo?',
    'auth.resetPassword': 'Gbeɖoɖo Gbɔ',
    'auth.noAccount': 'Wò Meɖe Kpe?',
    'auth.haveAccount': 'Wò Meɖe Kpe?',
    'auth.createAccount': 'Ŋlɔ Kpe',
    'auth.signInToAccount': 'Trɔ wò kpe me',

    // Form validation
    'validation.required': 'Ɖo nu aɖe',
    'validation.email': 'Ɖo email a ɖe',
    'validation.passwordLength': 'Gbeɖoɖo nɔ 6',
    'validation.passwordMatch': 'Gbeɖoɖo meɖe',
    'validation.phone': 'Ɖo telefon a ɖe',

    // Common
    'common.loading': 'Ɖo...',
    'common.error': 'Vɔɖe',
    'common.success': 'Wɔ',
    'common.welcome': 'Woezɔ',
    'common.hello': 'Hɛlo',
    'common.yes': 'Ɛɛ',
    'common.no': 'Oo',
    'common.save': 'Ɖo',
    'common.edit': 'Trɔ',
    'common.delete': 'Tso',
    'common.close': 'Xo',
    'common.back': 'Trɔ gbɔ',
    'common.next': 'Eɖo',
    'common.previous': 'Eɖo gbɔ',

    // Home page
    'home.hero.title': 'Ghana Agbledede AI Boa',
    'home.hero.subtitle': 'Yɛ Agbledede Nyawo Wɔ Ghana Me Wɔ Kasa Nyui Me',
    'home.hero.description': 'Ƒa agbledede nyawo, weather nyawo, market bo, kple nu bubu wò kasa me',
    'home.hero.getStarted': 'Fie Yɛ',
    'home.hero.learnMore': 'Ƒa Nyawo',

    // Features
    'features.title': 'Nuwɔwɔ',
    'features.chat.title': 'AI Chat Boa',
    'features.chat.description': 'Ƒa agbledede nyawo wò kasa me',
    'features.weather.title': 'Weather Nyawo',
    'features.weather.description': 'Weather nyawo wò be me',
    'features.market.title': 'Market Bo',
    'features.market.description': 'Ƒa crop bo a ɖe',
    'features.subsidies.title': 'Agbledede Boa',
    'features.subsidies.description': 'Nyawo wɔ government boa a ɖe',

    // Language selector
    'language.title': 'Paw Wò Kasa',
    'language.english': 'English',
    'language.twi': 'Twi',
    'language.ewe': 'Ewe',
    'language.ga': 'Ga',
    'language.fante': 'Fante',
    'language.french': 'French',

    // Profile
    'profile.title': 'Wò Nyawo',
    'profile.edit': 'Trɔ Wò Nyawo',
    'profile.personalInfo': 'Wò Nyawo',
    'profile.accountInfo': 'Kpe Nyawo',
    'profile.updateSuccess': 'Wò nyawo wɔ',
    'profile.updateError': 'Wò nyawo meɖe',

    // Chat
    'chat.title': 'AI Chat Boa',
    'chat.placeholder': 'Bisa nu aɖe wɔ agbledede ŋu...',
    'chat.send': 'Ɖo',
    'chat.clear': 'Tso Chat',
    'chat.loading': 'AI ɖo ade...',

    // Weather
    'weather.title': 'Weather Nyawo',
    'weather.current': 'Weather a ɖe',
    'weather.forecast': 'Weather a ɖo',
    'weather.temperature': 'Temperatur',
    'weather.humidity': 'Humidity',
    'weather.description': 'Nyawo',

    // Market
    'market.title': 'Market Bo',
    'market.crop': 'Crop',
    'market.price': 'Bo',
    'market.location': 'Be',
    'market.lastUpdated': 'Eɖo',

    // Subsidies
    'subsidies.title': 'Agbledede Boa',
    'subsidies.available': 'Program a ɖe',
    'subsidies.amount': 'Bo',
    'subsidies.deadline': 'Application Deadline',
    'subsidies.eligibility': 'Eligibility Criteria',

    // Team
    'team.title': 'Mía Team',
    'team.description': 'Ƒa developers a ɖe Farm Talk Ghana',
    'team.carlos.title': 'Carlos',
    'team.carlos.role': 'Full Stack Developer',
    'team.sefa.title': 'Sefakor',
    'team.sefa.role': 'Frontend Developer',

    // Footer
    'footer.copyright': '© 2024 Farm Talk Ghana. Mía rights nyinaa.',
    'footer.developed': 'Yɛɛ wɔ Level 400 Students, Ho Technical University',
  },

  ga: {
    // Navigation
    'nav.home': 'Shi',
    'nav.features': 'Nshɔŋmɔ',
    'nav.login': 'Kɛ Shɛ',
    'nav.signup': 'Kɛ Shɛ',
    'nav.profile': 'Wò Nyawo',
    'nav.logout': 'Kɛ',

    // Authentication
    'auth.signin': 'Kɛ Shɛ',
    'auth.signup': 'Kɛ Shɛ',
    'auth.email': 'Email',
    'auth.password': 'Gbeɖoɖo',
    'auth.confirmPassword': 'Gbeɖoɖo Gbɔ',
    'auth.fullName': 'Wò Ŋkɔ Nyui',
    'auth.phone': 'Wò Telefon',
    'auth.location': 'Be',
    'auth.role': 'Wò Dɔ',
    'auth.role.farmer': 'Agbledela',
    'auth.role.expert': 'Gbetɔ',
    'auth.role.customer': 'Dɔla',
    'auth.submit': 'Ɖo',
    'auth.cancel': 'Tso',
    'auth.forgotPassword': 'Wò Gbeɖoɖo?',
    'auth.resetPassword': 'Gbeɖoɖo Gbɔ',
    'auth.noAccount': 'Wò Meɖe Kpe?',
    'auth.haveAccount': 'Wò Meɖe Kpe?',
    'auth.createAccount': 'Ŋlɔ Kpe',
    'auth.signInToAccount': 'Kɛ wò kpe me',

    // Form validation
    'validation.required': 'Ɖo nu aɖe',
    'validation.email': 'Ɖo email a ɖe',
    'validation.passwordLength': 'Gbeɖoɖo nɔ 6',
    'validation.passwordMatch': 'Gbeɖoɖo meɖe',
    'validation.phone': 'Ɖo telefon a ɖe',

    // Common
    'common.loading': 'Ɖo...',
    'common.error': 'Vɔɖe',
    'common.success': 'Wɔ',
    'common.welcome': 'Woezɔ',
    'common.hello': 'Hɛlo',
    'common.yes': 'Ɛɛ',
    'common.no': 'Oo',
    'common.save': 'Ɖo',
    'common.edit': 'Trɔ',
    'common.delete': 'Tso',
    'common.close': 'Xo',
    'common.back': 'Trɔ gbɔ',
    'common.next': 'Eɖo',
    'common.previous': 'Eɖo gbɔ',

    // Home page
    'home.hero.title': 'Ghana Agbledede AI Boa',
    'home.hero.subtitle': 'Yɛ Agbledede Nyawo Wɔ Ghana Me Wɔ Kasa Nyui Me',
    'home.hero.description': 'Ƒa agbledede nyawo, weather nyawo, market bo, kple nu bubu wò kasa me',
    'home.hero.getStarted': 'Fie Yɛ',
    'home.hero.learnMore': 'Ƒa Nyawo',

    // Features
    'features.title': 'Nuwɔwɔ',
    'features.chat.title': 'AI Chat Boa',
    'features.chat.description': 'Ƒa agbledede nyawo wò kasa me',
    'features.weather.title': 'Weather Nyawo',
    'features.weather.description': 'Weather nyawo wò be me',
    'features.market.title': 'Market Bo',
    'features.market.description': 'Ƒa crop bo a ɖe',
    'features.subsidies.title': 'Agbledede Boa',
    'features.subsidies.description': 'Nyawo wɔ government boa a ɖe',

    // Language selector
    'language.title': 'Paw Wò Kasa',
    'language.english': 'English',
    'language.twi': 'Twi',
    'language.ewe': 'Ewe',
    'language.ga': 'Ga',
    'language.fante': 'Fante',
    'language.french': 'French',

    // Profile
    'profile.title': 'Wò Nyawo',
    'profile.edit': 'Trɔ Wò Nyawo',
    'profile.personalInfo': 'Wò Nyawo',
    'profile.accountInfo': 'Kpe Nyawo',
    'profile.updateSuccess': 'Wò nyawo wɔ',
    'profile.updateError': 'Wò nyawo meɖe',

    // Chat
    'chat.title': 'AI Chat Boa',
    'chat.placeholder': 'Bisa nu aɖe wɔ agbledede ŋu...',
    'chat.send': 'Ɖo',
    'chat.clear': 'Tso Chat',
    'chat.loading': 'AI ɖo ade...',

    // Weather
    'weather.title': 'Weather Nyawo',
    'weather.current': 'Weather a ɖe',
    'weather.forecast': 'Weather a ɖo',
    'weather.temperature': 'Temperatur',
    'weather.humidity': 'Humidity',
    'weather.description': 'Nyawo',

    // Market
    'market.title': 'Market Bo',
    'market.crop': 'Crop',
    'market.price': 'Bo',
    'market.location': 'Be',
    'market.lastUpdated': 'Eɖo',

    // Subsidies
    'subsidies.title': 'Agbledede Boa',
    'subsidies.available': 'Program a ɖe',
    'subsidies.amount': 'Bo',
    'subsidies.deadline': 'Application Deadline',
    'subsidies.eligibility': 'Eligibility Criteria',

    // Team
    'team.title': 'Mía Team',
    'team.description': 'Ƒa developers a ɖe Farm Talk Ghana',
    'team.carlos.title': 'Carlos',
    'team.carlos.role': 'Full Stack Developer',
    'team.sefa.title': 'Sefakor',
    'team.sefa.role': 'Frontend Developer',

    // Footer
    'footer.copyright': '© 2024 Farm Talk Ghana. Mía rights nyinaa.',
    'footer.developed': 'Yɛɛ wɔ Level 400 Students, Ho Technical University',
  },

  fa: {
    // Navigation
    'nav.home': 'Fie',
    'nav.features': 'Nneɛma',
    'nav.login': 'Bra Mu',
    'nav.signup': 'Fie Kyerɛw',
    'nav.profile': 'Wo Ho Nsɛm',
    'nav.logout': 'Pue',

    // Authentication
    'auth.signin': 'Bra Mu',
    'auth.signup': 'Fie Kyerɛw',
    'auth.email': 'Email',
    'auth.password': 'Password',
    'auth.confirmPassword': 'San Password No',
    'auth.fullName': 'Wo Din Nyinaa',
    'auth.phone': 'Wo Phone Number',
    'auth.location': 'Bea',
    'auth.role': 'Wo Role',
    'auth.role.farmer': 'Kuayefo',
    'auth.role.expert': 'Nimdefo',
    'auth.role.customer': 'Tɔnfo',
    'auth.submit': 'Fa',
    'auth.cancel': 'Gyae',
    'auth.forgotPassword': 'Wo Wɔ Password?',
    'auth.resetPassword': 'San Password No',
    'auth.noAccount': 'Wo Nni Account?',
    'auth.haveAccount': 'Wo Wɔ Account?',
    'auth.createAccount': 'Yɛ Account',
    'auth.signInToAccount': 'Bra wo account mu',

    // Form validation
    'validation.required': 'Yɛ wo sɛ wo de biribi fa',
    'validation.email': 'Fa email a ɛyɛ yie',
    'validation.passwordLength': 'Password nso yɛ mma 6',
    'validation.passwordMatch': 'Password no nso yɛ yie',
    'validation.phone': 'Fa phone number a ɛyɛ yie',

    // Common
    'common.loading': 'Reyɛ...',
    'common.error': 'Nsɛm',
    'common.success': 'Yɛɛ yie',
    'common.welcome': 'Akwaaba',
    'common.hello': 'Hɛlo',
    'common.yes': 'Aane',
    'common.no': 'Daabi',
    'common.save': 'Fa',
    'common.edit': 'Sesa',
    'common.delete': 'Sɛɛ',
    'common.close': 'To',
    'common.back': 'San',
    'common.next': 'Akyiri',
    'common.previous': 'Akan',

    // Home page
    'home.hero.title': 'Ghana Kuayɛ AI Boa',
    'home.hero.subtitle': 'Yɛ Kuayɛ Nsɛm Wɔ Ghana Mu Wɔ Kasa Nyinaa Mu',
    'home.hero.description': 'Fa kuayɛ nimdeɛ, weather nsɛm, market bo, ne nneɛma foforɔ wɔ wo kasa mu',
    'home.hero.getStarted': 'Fie Yɛ',
    'home.hero.learnMore': 'Fa Nimdeɛ',

    // Features
    'features.title': 'Nneɛma',
    'features.chat.title': 'AI Chat Boa',
    'features.chat.description': 'Fa kuayɛ nimdeɛ wɔ wo kasa mu',
    'features.weather.title': 'Weather Nsɛm',
    'features.weather.description': 'Weather nsɛm wɔ wo bea mu',
    'features.market.title': 'Market Bo',
    'features.market.description': 'Fa crop bo a ɛwɔ sɛ',
    'features.subsidies.title': 'Kuayɛ Boa',
    'features.subsidies.description': 'Nsɛm wɔ government boa a ɛwɔ',

    // Language selector
    'language.title': 'Paw Wo Kasa',
    'language.english': 'English',
    'language.twi': 'Twi',
    'language.ewe': 'Ewe',
    'language.ga': 'Ga',
    'language.fante': 'Fante',
    'language.french': 'French',

    // Profile
    'profile.title': 'Wo Ho Nsɛm',
    'profile.edit': 'Sesa Wo Ho Nsɛm',
    'profile.personalInfo': 'Wo Ho Nsɛm',
    'profile.accountInfo': 'Account Nsɛm',
    'profile.updateSuccess': 'Wo ho nsɛm yɛɛ yie',
    'profile.updateError': 'Wo ho nsɛm nso yɛɛ yie',

    // Chat
    'chat.title': 'AI Chat Boa',
    'chat.placeholder': 'Bisa me biribi wɔ kuayɛ ho...',
    'chat.send': 'Fa',
    'chat.clear': 'Sɛɛ Chat',
    'chat.loading': 'AI reyɛ adwene...',

    // Weather
    'weather.title': 'Weather Nsɛm',
    'weather.current': 'Weather a ɛwɔ sɛ',
    'weather.forecast': 'Weather a ɛbɛba',
    'weather.temperature': 'Temperatur',
    'weather.humidity': 'Humidity',
    'weather.description': 'Nkyerɛ',

    // Market
    'market.title': 'Market Bo',
    'market.crop': 'Crop',
    'market.price': 'Bo',
    'market.location': 'Bea',
    'market.lastUpdated': 'Sɛɛɛ',

    // Subsidies
    'subsidies.title': 'Kuayɛ Boa',
    'subsidies.available': 'Program a ɛwɔ',
    'subsidies.amount': 'Bo',
    'subsidies.deadline': 'Application Deadline',
    'subsidies.eligibility': 'Eligibility Criteria',

    // Team
    'team.title': 'Yɛn Team',
    'team.description': 'Fa developers a ɛyɛɛ Farm Talk Ghana',
    'team.carlos.title': 'Carlos',
    'team.carlos.role': 'Full Stack Developer',
    'team.sefa.title': 'Sefakor',
    'team.sefa.role': 'Frontend Developer',

    // Footer
    'footer.copyright': '© 2024 Farm Talk Ghana. Yɛn rights nyinaa.',
    'footer.developed': 'Yɛɛɛ wɔ Level 400 Students, Ho Technical University',
  },

  fr: {
    // Navigation
    'nav.home': 'Accueil',
    'nav.features': 'Fonctionnalités',
    'nav.login': 'Connexion',
    'nav.signup': 'Inscription',
    'nav.profile': 'Profil',
    'nav.logout': 'Déconnexion',

    // Authentication
    'auth.signin': 'Se Connecter',
    'auth.signup': 'S\'inscrire',
    'auth.email': 'Email',
    'auth.password': 'Mot de passe',
    'auth.confirmPassword': 'Confirmer le mot de passe',
    'auth.fullName': 'Nom complet',
    'auth.phone': 'Numéro de téléphone',
    'auth.location': 'Localisation',
    'auth.role': 'Rôle',
    'auth.role.farmer': 'Agriculteur',
    'auth.role.expert': 'Expert',
    'auth.role.customer': 'Client',
    'auth.submit': 'Soumettre',
    'auth.cancel': 'Annuler',
    'auth.forgotPassword': 'Mot de passe oublié?',
    'auth.resetPassword': 'Réinitialiser le mot de passe',
    'auth.noAccount': 'Vous n\'avez pas de compte?',
    'auth.haveAccount': 'Vous avez déjà un compte?',
    'auth.createAccount': 'Créer un compte',
    'auth.signInToAccount': 'Connectez-vous à votre compte',

    // Form validation
    'validation.required': 'Ce champ est requis',
    'validation.email': 'Veuillez entrer un email valide',
    'validation.passwordLength': 'Le mot de passe doit contenir au moins 6 caractères',
    'validation.passwordMatch': 'Les mots de passe ne correspondent pas',
    'validation.phone': 'Veuillez entrer un numéro de téléphone valide',

    // Common
    'common.loading': 'Chargement...',
    'common.error': 'Erreur',
    'common.success': 'Succès',
    'common.welcome': 'Bienvenue',
    'common.hello': 'Bonjour',
    'common.yes': 'Oui',
    'common.no': 'Non',
    'common.save': 'Sauvegarder',
    'common.edit': 'Modifier',
    'common.delete': 'Supprimer',
    'common.close': 'Fermer',
    'common.back': 'Retour',
    'common.next': 'Suivant',
    'common.previous': 'Précédent',

    // Home page
    'home.hero.title': 'Assistant IA Agricole pour le Ghana',
    'home.hero.subtitle': 'Bridging Communication in Ghana\'s Agriculture through multilingual AI assistance',
    'home.hero.description': 'Obtenez des conseils d\'experts en agriculture, des mises à jour météo, des prix du marché et plus dans votre langue préférée',
    'home.hero.getStarted': 'Commencer',
    'home.hero.learnMore': 'En savoir plus',

    // Features
    'features.title': 'Fonctionnalités',
    'features.chat.title': 'Assistant IA Chat',
    'features.chat.description': 'Obtenez des conseils agricoles instantanés dans votre langue',
    'features.weather.title': 'Mises à jour météo',
    'features.weather.description': 'Informations météorologiques en temps réel pour votre localisation',
    'features.market.title': 'Prix du marché',
    'features.market.description': 'Restez informé des prix actuels des cultures',
    'features.subsidies.title': 'Subventions agricoles',
    'features.subsidies.description': 'Informations sur le soutien gouvernemental disponible',

    // Language selector
    'language.title': 'Choisissez Votre Langue',
    'language.english': 'Anglais',
    'language.twi': 'Twi',
    'language.ewe': 'Ewe',
    'language.ga': 'Ga',
    'language.fante': 'Fante',
    'language.french': 'Français',

    // Profile
    'profile.title': 'Profil utilisateur',
    'profile.edit': 'Modifier le profil',
    'profile.personalInfo': 'Informations personnelles',
    'profile.accountInfo': 'Informations du compte',
    'profile.updateSuccess': 'Profil mis à jour avec succès',
    'profile.updateError': 'Échec de la mise à jour du profil',

    // Chat
    'chat.title': 'Assistant IA Chat',
    'chat.placeholder': 'Demandez-moi n\'importe quoi sur l\'agriculture...',
    'chat.send': 'Envoyer',
    'chat.clear': 'Effacer le chat',
    'chat.loading': 'L\'IA réfléchit...',

    // Weather
    'weather.title': 'Informations météorologiques',
    'weather.current': 'Météo actuelle',
    'weather.forecast': 'Prévisions',
    'weather.temperature': 'Température',
    'weather.humidity': 'Humidité',
    'weather.description': 'Description',

    // Market
    'market.title': 'Prix du marché',
    'market.crop': 'Culture',
    'market.price': 'Prix',
    'market.location': 'Localisation',
    'market.lastUpdated': 'Dernière mise à jour',

    // Subsidies
    'subsidies.title': 'Subventions agricoles',
    'subsidies.available': 'Programmes disponibles',
    'subsidies.amount': 'Montant',
    'subsidies.deadline': 'Date limite de candidature',
    'subsidies.eligibility': 'Critères d\'éligibilité',

    // Team
    'team.title': 'Notre équipe',
    'team.description': 'Rencontrez les développeurs derrière Farm Talk Ghana',
    'team.carlos.title': 'Carlos',
    'team.carlos.role': 'Développeur Full Stack',
    'team.sefa.title': 'Sefakor',
    'team.sefa.role': 'Développeur Frontend',

    // Footer
    'footer.copyright': '© 2024 Farm Talk Ghana. Tous droits réservés.',
    'footer.developed': 'Développé par les étudiants de niveau 400, Ho Technical University',
  },
};

export const LanguageProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [language, setLanguage] = useState<Language>('en');

  // Load language from localStorage on mount
  useEffect(() => {
    const savedLanguage = localStorage.getItem('farm-talk-language') as Language;
    if (savedLanguage && ['en', 'tw', 'ee', 'ga', 'fa', 'fr'].includes(savedLanguage)) {
      setLanguage(savedLanguage);
    }
  }, []);

  // Save language to localStorage when it changes
  const handleLanguageChange = (newLanguage: Language) => {
    setLanguage(newLanguage);
    localStorage.setItem('farm-talk-language', newLanguage);
  };

  // Translation function
  const t = (key: string): string => {
    return translations[language][key] || key;
  };

  const value: LanguageContextType = {
    language,
    setLanguage: handleLanguageChange,
    t,
  };

  return (
    <LanguageContext.Provider value={value}>
      {children}
    </LanguageContext.Provider>
  );
};

export const useLanguage = (): LanguageContextType => {
  const context = useContext(LanguageContext);
  if (context === undefined) {
    throw new Error('useLanguage must be used within a LanguageProvider');
  }
  return context;
};
