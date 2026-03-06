# Maven Central Publishing

After a release is complete and tagged:

1. Uncomment and update the signing properties in `gradle.properties`.
2. Run `./gradlew publish`.
3. Run
   `curl -X POST -u "username:token" "https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/com.github.mrbean355"`
    - Note: use `curl.exe` on Windows.
4. Log in to the [Maven Central portal](https://central.sonatype.com/publishing).
5. Publish the release.
