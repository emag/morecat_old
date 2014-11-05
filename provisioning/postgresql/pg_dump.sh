#!/bin/sh

TARGET_DB=morecat
BACKUP_DIR=/var/backups/pg_dump_files
LOG_FILE=${BACKUP_DIR}/pg_dump.log

echo >>$LOG_FILE
echo `date` "[ pg_dump Start. ]" >>$LOG_FILE
DATE=`date +%Y%m%d`

for db_name in $TARGET_DB
do
  dump_file_path=$BACKUP_DIR/${db_name}.${DATE}
  /usr/pgsql-9.3/bin/pg_dump $db_name -b -o -Fc > $dump_file_path 2>>$LOG_FILE
  echo `date` "[ Database $db_name Backup Complete. ]" >>$LOG_FILE
done

echo `date` "[ pg_dump Complete. ]" >> $LOG_FILE
