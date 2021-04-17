#!/usr/bin/env ruby -w --encoding=utf-8
# -w turn warnings

# Usage:
# ./build-detekt-report.rb
# The report file will be generated at detekt/report/detekt-report.html:


BASE_DIR = Dir.pwd;
SCRIPT_DIR = BASE_DIR + "/script";

require 'pp'
require 'fileutils'

def generateDetektReport()
    setUpDetektCLI()

    # generate report
    system "detekt -c detekt/config.yml -r html:detekt/reports/detekt-report.html"
end

def setUpDetektCLI()
    puts "setUpDetektCLI"
    system "brew -v"
    system "brew update"
    puts "brew update complete"
    system "brew install detekt"
    puts "detekt installed"
end

generateDetektReport()