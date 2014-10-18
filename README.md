# morecat

## Tasks

### release

~~~
./gradlew -PprofileName=postgresql clean release
~~~

### generate-schema

~~~
./gradlew -PprofileName=generate-schema clean generateSchema
~~~

## Provisioning

### PostgreSQL

You can use Ansible. Please create a `hosts` file that have in your hosts.

e.g.)

~~~
[postgresql]
192.168.33.10
~~~

And you need to edit `sudo` and `user` fields in `playbook_postgresql.yml`.

Then, run ansible.

~~~
ansible-playbook -i /path/to/hosts provisioning/postgresql/playbook_postgresql.yml
~~~
