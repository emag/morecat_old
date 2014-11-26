# MoreCat

MoreCat is a blog APIs.

## Usage

### Setup Keycloak

MoreCat depends on [Keycloak](http://keycloak.jboss.org/).

### Create Keycloak configuration

`createKeycloakConfiguration` task adds `keycloak.json` to `WEB-INF`.

~~~ sh
./gradlew createKeycloakConfiguration \
          -PkeycloakAuthServerUrl=<Keycloak Auth Server URL> \
          -PkeycloakRealmPublicKey=<Keycloak MoreCat Realm Public Key> \
          -PkeycloakCredentialSecret=<Keycloak MoreCat Credential>
~~~

e.g.

~~~ sh
./gradlew createKeycloakConfiguration \
          -PkeycloakAuthServerUrl=http://localhost:8080/auth \
          -PkeycloakRealmPublicKey=MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdHmaWd7ODL4tict3eB9SXOU59K1Jo/kdcnmTcIr0rfTpNdaSbKaQ8Iai9hNfe8nC/iTxerYqMgmBW6CwIUFOk7ZYZhP2hr6gtYIpCUkpusmbm+R0CmXW6/x3aWw9Iraz/tPGQv9CptbLAhy5OMKgn+9CI3LBzzM+jJna/grki9QIDAQAB \
          -PkeycloakCredentialSecret=ea16fd7b-5752-4347-a5b6-0a15b16e64e9
~~~

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
