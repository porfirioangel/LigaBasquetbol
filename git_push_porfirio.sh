#! /usr/bin/expect

set timeout -1
set username "porfirioads"
set password "\$Pads.96\$_gitlab" 
set name "Porfirio Ángel Díaz Sánchez"
set email "porfirioads@gmail.com"
set commit_message [lindex $argv 0]

spawn git config --global user.name "$name"
expect eof
spawn git config --global user.email "$email"
expect eof
spawn git add .
expect eof
spawn git commit -m "$commit_message "
expect eof
spawn git push origin master

expect "Username"
send $username\n
 
expect "Password"
send $password\n
 
expect eof

