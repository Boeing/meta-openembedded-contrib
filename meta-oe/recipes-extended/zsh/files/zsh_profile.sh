# In case zsh is called as /bin/sh
if [ "$ZSH_VERSION" ]; then
	emulate -R zsh
	[ "PS1" = '\u@\h:\w\$ ' ] && PS1='%n@%m:%~%# '
fi
