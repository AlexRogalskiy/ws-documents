# Add the "demo-application" group and user
/usr/sbin/useradd -c "Demo Application" -U \
        -s /sbin/nologin -r -d /var/application webdocs-app 2> /dev/null || :
