## sfx-google-enterprise

sfx-google-enterprise is a wrapper aound Google Android Enterprise Java library. It helps simplify the process of creating an enterprise, creating policies and provisioning devices.

### Dependency
```
<dependency>
  <groupId>com.seamfix.sdk</groupId>
  <artifactId>sfx-google-enterprise</artifactId>
  <version>0.0.1</version>
</dependency>
```
### Implementation
Initialize the library
```
 SfxAndroidEnterprise.init("PATH TO YOUR SERVICE ACCOUNT", "YOUR APP NAME");
 ```
 
 ### Create an enterprise
 ```
 Result result = SfxAndroidEnterprise.createEnterprise("veyanjeffmdmproject", "https://www.wwe.com");
 //Redirect the user to this url to begin the signup process:
 System.out.println("*******: SignupUrl name -> "+result.getSignUpUrl().getName());
 System.out.println("*******: SignupUrl url -> "+result.getSignUpUrl().getUrl());
 ```
 
 ### Complete enterprise creation
 ```
 SignupUrl signupUrl = new SignupUrl();
 signupUrl.setName("");
 signupUrl.setUrl("");
 Result r = SfxAndroidEnterprise.completeEnterpriseCreation("",signupUrl, "");
 System.out.println("*******: Enterprise created with name -> "+r.getEnterpriseName());
 ```
 
 ### Create a policy
 ```
 SfxAndroidEnterprise.createPolicy("YOUR ENTERPRISE NAME", "POLICY ID", new Policy());
 ```
 An example of how to create a Policy that installs Bioregistra can be seen here:
 https://github.com/seamfix/SampleAndroidEnterprise/blob/61ba4b09aded5dfa10ed7498ca5c594be4b781dd/src/main/java/SampleAppTest.java#L131
 
 ### Provision a device
 ```
 String url = SfxAndroidEnterprise.createEnrollmentToken(CreateEnrollmentToken.OutputType.URL_STRING, "YOUR ENTERPRISE NAME", "POLICY ID");
 System.out.println("*******: enrollment token: "+url);
 ```
 
 ### Provisioning a device
 There are two modes:
 #### QR Code (for fully managed mode)
 ```
 String qrCodeDetails = SfxAndroidEnterprise.createEnrollmentToken(CreateEnrollmentToken.OutputType.QR_CODE, "enterprise name", "policy ID");
 ```
 Use the  ```qrCodeDetails``` to generate a qr code. Reset your device and scan the qr code.
 
 #### URL link (for work profile mode)
 ```
 String enrollmentToken = SfxAndroidEnterprise.createEnrollmentToken(CreateEnrollmentToken.OutputType.URL_STRING, "enterprise name", "policy ID");
 ```
To provision a device in work profile mode, the following link should be opened on the browser of the device:
https://enterprise.google.com/android/enroll?et=YOUR_ENROLLMENT_TOKEN
