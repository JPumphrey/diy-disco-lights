# diy-disco-lights
Stay home? Still party. Makes hue lights flash

## Instructions
 - Follow Philips' instructions to get your IP address and username https://developers.meethue.com/develop/get-started-2/
 - Create a file `secrets.properties` and add them there as follows
 ```
 defaultip=YOUR_BRIDGE_IP_ADDRESS
 defaultusername=YOUR_USERNAME_STRING
 ```
When the setup tab is complete, you should be able to set them there instead. You may also need to set your light ID numbers in `HomeFragment.java`.
