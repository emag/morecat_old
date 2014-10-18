[ -f /etc/profile ] && source /etc/profile
PGDATA=/dbfp/pgdata/data
export PGDATA

export PATH=/usr/pgsql-9.3/bin:$PATH
