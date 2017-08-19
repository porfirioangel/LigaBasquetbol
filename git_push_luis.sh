#! /usr/bin/expect

set timeout -1
set username "luis.cristerna14"
set password "luisV@@din" 
set name "Luis Manuel Cristerna Gallegos"
set email "luis.cristerna14@gmail.com"
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

spawn git config --global user.name "Porfirio Ángel Díaz Sánchez"
expect eof
spawn git config --global user.email "porfirioads@gmail.com"
expect eof

