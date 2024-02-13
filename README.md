
# WMS commons dedupe persistence

The commons dedupe persistence module provides a Dedupe entity and a repository for the same that can be used to check for duplicates. 

For more details, kindly refer the design doc.

[Low Level Design Documentation](https://autozone1com.sharepoint.com/:w:/r/sites/SupplyChainWMSRewrite-AZRIMS/Shared%20Documents/AZ%20RIMS%20-%20WMS%20ReBuild%20SR%20and%20AZ/Scope%20Retail%20Documents/Design/LLD/Common/LLD_Common_v0.2.docx?d=w88756a53e5c84212997e94674ee678c1&csf=1&web=1&e=E7Xv8t)

## System Requirements

### Local Development Environment

- Maven version 3.5+.
- Java Version 17 JDK.
- Intellij Community Edition, Spring Tools IDE, Eclipse IDE or VSCode

## Usage/Examples

### Importing commons dedupe persistence

- Ensure you have the below repositories setup for the WMS service you are about to implement:-

```bash
wms-template-service
    |_wms-template-message-handler
            |_wms-template-service-layer
                |_wms-template-io
                |_wms-template-dao
```

- ```wms-commons-dedupe-persistence``` already comes as a dependency of commons-message-consumer. So you only need to include ```wms-commons-message-consumer``` in your ```wms-template-message-handler```. The message consumer/or any other service, can use the commons dedupe persistence, by including the internal and external bom like so

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>az.supplychain.wms</groupId>
            <artifactId>wms-bom-external</artifactId>
            <version>${wms-bom-external.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>az.supplychain.wms</groupId>
            <artifactId>wms-bom-internal</artifactId>
            <version>${wms-bom-internal.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

```
- Finally include the dedupe persistence under the dependencies section like so

```xml    
<dependencies>
		<dependency>
			<groupId>az.supplychain.wms</groupId>
			<artifactId>wms-commons-dedupe-persistence</artifactId>
		</dependency>
</dependencies>
```
### Using the commons dedupe persistence
- To check if your payload is duplicate, you can create a hash of the received payload using a hash of your choice and find like so

```java
import az.supplychain.wms.commons.dedupe.persistence.repository.DedupeKeyRepository;

public class SomeConsumer {

    @Autowired
    private DedupeKeyRepository dedupeRepository;

    private void checkDuplicate(final AsyncMessage asyncMessage) {
        //generate hash of payload
        final String dedupeKey =
                DigestUtils.md5Hex(asyncMessage.getHeaders() + asyncMessage.getRawBody());

        final Optional<DedupeKey> optDedupeKey = dedupeRepository.findByLogKey(dedupeKey);

        if (optDedupeKey.isPresent()) {
          //handle duplicate payload scenario
        }
    }
}
```
- You can create a dedupe key entry like so

```java

public class SomeConsumer {

    @Autowired
    private DedupeKeyRepository dedupeRepository;

    private void payloadReceived(final AsyncMessage asyncMessage) {
        //generate hash of payload
        final String dedupeKey =
                DigestUtils.md5Hex(asyncMessage.getHeaders() + asyncMessage.getRawBody());
        ...

        //Make entry of key in dedupe table to check for duplicates for payload in future
        final DedupeKey dedupeKeyEntity = new DedupeKey();
        dedupeKeyEntity.setLogKey(dedupeKey);
        dedupeRepository.save(dedupeKeyEntity);
    }
}

```

 - You can also delete the dedupe key entry like so

 ```java

public class SomeConsumer {

    @Autowired
    private DedupeKeyRepository dedupeRepository;

    private void deleteDedupeKey(final AsyncMessage asyncMessage) {
        //generate hash of payload
        final String dedupeKey =
                DigestUtils.md5Hex(asyncMessage.getHeaders() + asyncMessage.getRawBody());
        ...
        //delete payload hash from db
        dedupeRepository.deleteBylogKey(dedupeKey);
    }
}

 ```

### Building the project
- After all changes are done, build the all projects like so:

```
cd your/project/root/dir

mvn clean install
```

## Roadmap

**Note:** At the moment, checking for duplicates requires 2 queries. One for checking and another for updating.

[ ] Need to R&D on UPSERT feature on google spanner. [WMSREBLD-2121](https://track.autozone.com/browse/WMSREBLD-2121)

[ ] Update the check duplicate method in dedupe persistence and use that in message consumer.[WMSREBLD-2122](https://track.autozone.com/browse/WMSREBLD-2122)
