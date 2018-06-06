#!/usr/bin/env ruby
# -*-Ruby-*-

require 'pry'
require "awesome_print"

@simulation=%Q[#{ENV["CACHEDIR"]}/#{ENV["simulation"]}]

Dir.chdir(@simulation)
files = Dir['*.tar']

pp "files:"
pp files

ftokens = files.map do |file|
  file.split(/[\s._]/)
end

terse = ftokens.transpose.map{ |words|
  uniq_word = words.uniq
  (uniq_word.length == 1) ? {uniq_word[0] => ""} : uniq_word.map{ |w| {w => w}}.reduce(:merge)
}

fterse = ftokens.map do |ftoken|
  zeta = terse.zip(ftoken)
  zeta.map{ |a,b| a[b]}.join("")
end

in_out = files.zip(fterse)

#pp in_out

# process the tar files
cmdline1 = in_out.map { |a|
  %x[
mkdir -p #{@simulation}/#{a[1]}
tar xf #{@simulation}/#{a[0]} --directory #{@simulation}/#{a[1]}
]
}

#puts cmdline1

# generate the command line
cmdline2 = in_out.map { |a|
  %Q[lein run #{@simulation}/#{a[1]} #{@simulation}/bj_#{a[1]}.csv]
}

puts cmdline2

job = "cd $HOME/projects/bpf\n" + cmdline2.join("\n")

puts "---"
puts "job"

puts job
