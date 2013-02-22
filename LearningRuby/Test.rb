require 'rbconfig'
include RbConfig
puts CONFIG["host"] # => "i386appledarwin9.6.0"
puts CONFIG["libdir"] # => "/usr/local/rubybook/lib"
  
π = 3.14159
puts "π = #{π}"