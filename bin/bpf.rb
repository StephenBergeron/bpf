#!/usr/bin/env ruby
# -*-Ruby-*-

require 'pry'
require "awesome_print"

# lein run $CACHEDIR/$simulation/77775.dat $CACHEDIR/$simulation/bj_proc.csv

Dir.chdir(%Q[#{ENV["CACHEDIR"]}/#{ENV["simulation"]}])

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


pp files.zip(fterse)
