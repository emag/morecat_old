# morecat

Java EE で実装されたブログシステムです。ブログに関わる API と管理用コンソールを提供します。

## 利用環境

* JDK 8 以降
* WildFly 8.1.0.Final
* PostgreSQL 9.3 or H2
 * ※ H2 の利用用途は主に開発・デモを想定しています。

## APIs

### エントリ情報取得

以下のような json を返します。複数のエントリを含む場合は、`createDate` と `createdTime` の降順にソートされます。

~~~
[
  {
    "title": "some-title",
    "content": "some-content",
    "permalink": "some-permalnk",
    "authorName": "someone",
    "createdDate": "yyyy-MM-dd",
    "createdTime": "HH:mm:ss",
    "tags": [ "foo", "bar" ]
  },
  {
    ...
  }
]
~~~

* `GET /api/entiris`
 * オプションのクエリとしてページネーションに利用する `start` と `size` があります。
  * 今後 json に `next` と `previous` 要素を用意する予定です。
* `GET /api/entries/:year`
* `GET /api/entries/:year/:month`
* `GET /api/entries/:year/:month/:day`
* `GET /api/entries/:year/:month/:day/:permalink`
 * 必ず 1 エントリのみを返します。
* `GET /api/entries/tags`
* `GET /api/entries/tags/:tag`

## 管理コンソール

管理コンソールでは以下を行えます。

* エントリの作成・更新
* ユーザの追加・更新

以下 URL にアクセスし、ログインします。

`http://<domain>:<port>/mc-admin/login.xhtml`

デフォルトのユーザとして、`admin` が存在します。ログイン ID は `admin@morec.at` パスワードは `morecat` です。

## インストール

ここでは Red Hat 系の Linux を前提としてインストール方法を説明します。

お手軽に環境をセットアップしたい場合は、以下の Vagrant + VirtualBox も利用できます。

https://github.com/emag/morecat-production

なお、Vagrant + VirtualBox を利用する場合は上記リポジトリの `centos65-wildfly81-postgresql93` タグをご利用ください。

### PostgreSQL 9.3 のセットアップ

DB に PostgreSQL を利用する場合は以下のデータベースを予め作成します。

* `morecat` ユーザ(パスワードも morecat)
* `morecat` データベース(オーナーは上記 `morecat` ユーザ)

### JDK 8 インスト‐ル

#### OpenJDK

※ Fedora のみ可

~~~ sh
yum -y install java-1.8.0-openjdk
~~~

#### Oracle JDK

~~~ sh
wget -O /tmp/jdk.rpm --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u11-b12/jdk-8u11-linux-x64.rpm
yum -y install /tmp/jdk.rpm
~~~

### WildFly インストール

~~~ sh
cd /path/to
wget http://download.jboss.org/wildfly/8.1.0.Final/wildfly-8.1.0.Final.zip
unzip wildfly-8.1.0.Final.zip
~~~

今後 zip 展開先を `$WILDFLY_HOME` とします。

後の設定のためにあらかじめ WildFly を管理者モードで起動しておきます。

~~~ sh
$WILDFLY_HOME/bin/standalone.sh --admin-only
~~~

### WildFly の MoreCat 用設定

~~~ sh
git clone https://github.com/emag/morecat.git
cd morecat
~~~

#### セキュリティ設定

~~~ sh
$WILDFLY_HOME/bin/jboss-cli.sh -c file=cli/security-domain.cli
~~~

#### データソース設定(PostgreSQL を利用する場合のみ)

あらかじめ [cli/datasource.cli の connection-url](https://github.com/emag/morecat/blob/0.1.6/cli/datasource.cli#L3) をご自分の環境に合わせて修正する必要があります。

~~~ sh
wget http://jdbc.postgresql.org/download/postgresql-9.3-1102.jdbc41.jar
$WILDFLY_HOME/bin/jboss-cli.sh -c command="deploy /path/to/postgresql-9.3-1102.jdbc41.jar"
$WILDFLY_HOME/bin/jboss-cli.sh -c file=/cli/datasource.cli
~~~

#### DB マイグレーション(PostgreSQL を利用する場合のみ)

あらかじめ [flyway.gradle の url](https://github.com/emag/morecat/blob/0.1.6/flyway.gradle#L14) をご自分の環境に合わせて修正する必要があります。

~~~ sh
./gradlew flywayMigrate
~~~

### MoreCat のビルド

#### PostgreSQL を利用する場合

~~~ sh
./gradlew -PprofileName=postgresql release
~~~

#### H2 を利用する場合

~~~ sh
./gradlew war
~~~

`build/libs` 以下に `morecat.war` が作成されます。

### WildFly へのデプロイ

~~~ sh
$WILDFLY_HOME/bin/jboss-cli.sh -c command="deploy build/libs/morecat.war"
~~~

デプロイが完了したら WildFly を再起動します(--admin-only は無し)。
