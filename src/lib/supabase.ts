import { createClient } from '@supabase/supabase-js'

const supabaseUrl = 'https://zdvmefsqanakzmebxijs.supabase.co'
const supabaseAnonKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inpkdm1lZnNxYW5ha3ptZWJ4aWpzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTQ5MzUwMTQsImV4cCI6MjA3MDUxMTAxNH0.te5kkP9KSR4_GIYoNWzb8vyRrTIG29g2pBzzW7xbMiQ'

console.log('Using real Supabase credentials')

export const supabase = createClient(supabaseUrl, supabaseAnonKey)

// Database types (you can generate these from your Supabase dashboard)
export interface Database {
  public: {
    Tables: {
      users: {
        Row: {
          id: string
          email: string
          full_name: string
          role: 'farmer' | 'expert' | 'customer'
          created_at: string
          updated_at: string
        }
        Insert: {
          id?: string
          email: string
          full_name: string
          role: 'farmer' | 'expert' | 'customer'
          created_at?: string
          updated_at?: string
        }
        Update: {
          id?: string
          email?: string
          full_name?: string
          role?: 'farmer' | 'expert' | 'customer'
          created_at?: string
          updated_at?: string
        }
      }
      chat_messages: {
        Row: {
          id: string
          user_id: string
          message: string
          response: string
          language: string
          created_at: string
        }
        Insert: {
          id?: string
          user_id: string
          message: string
          response: string
          language?: string
          created_at?: string
        }
        Update: {
          id?: string
          user_id?: string
          message?: string
          response?: string
          language?: string
          created_at?: string
        }
      }
      weather_data: {
        Row: {
          id: string
          location: string
          temperature: number
          humidity: number
          description: string
          created_at: string
        }
        Insert: {
          id?: string
          location: string
          temperature: number
          humidity: number
          description: string
          created_at?: string
        }
        Update: {
          id?: string
          location?: string
          temperature?: number
          humidity?: number
          description?: string
          created_at?: string
        }
      }
      market_prices: {
        Row: {
          id: string
          crop_name: string
          price: number
          unit: string
          location: string
          created_at: string
        }
        Insert: {
          id?: string
          crop_name: string
          price: number
          unit: string
          location: string
          created_at?: string
        }
        Update: {
          id?: string
          crop_name?: string
          price?: number
          unit?: string
          location?: string
          created_at?: string
        }
      }
    }
  }
}

export type Tables<T extends keyof Database['public']['Tables']> = Database['public']['Tables'][T]['Row']
export type Inserts<T extends keyof Database['public']['Tables']> = Database['public']['Tables'][T]['Insert']
export type Updates<T extends keyof Database['public']['Tables']> = Database['public']['Tables'][T]['Update']



