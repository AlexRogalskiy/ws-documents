# How to deploy a new version

1. Adjust `settings.xml`

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">
    <servers>
        <server>
            <id>registry.hub.docker.com</id>
            <username>${env.DOCKERHUB_USERNAME}</username>
            <password>${env.DOCKERHUB_PASSWORD}</password>
        </server>
    </servers>
</settings>
```

2. Ensure GPG Key is uploaded to a Keyserver

```
gpg --gen-key
gpg --send-keys
```

3. Deploy the project

```bash
export GPG_TTY=$(tty)
mvn clean deploy
```
