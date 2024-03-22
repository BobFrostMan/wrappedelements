# Motivation
As a test automation engineer I hate to write synchronization and wait code, so I want to create quick and stable ui autotests.
I need a robust semantically laconic ui framework that saves me a lot of time during ui web test automation.
I hate to spend time in debug and adjust waiters. I want to write tests quickly and easily.

# For whom?
For test automation engineer who:
- Wants to write UI test once and doesn't want to support it anymore
- Hates duplications 
- Hates to write a lot of boilerplate code
- Likes clean and simple looking code
- Hates routine and tries to avoid it

# Features list:
- [X] Clear and simple Page object classes (juicy)
- [X] Easy element interaction condition definition on Page object level (juicy)
- [X] Easy element waiters and timeouts configuration on Page object level (juicy)
- [X] No element synchronization code on test level as result clear code on test level (juicy)
- [X] Extendable custom element configuration and customization system (nice to have)
- [X] Dynamic locators support (nice to have)
- [X] Automatic web driver recreation if it's dead or quit (not necessary but nice for some cases)
- [X] Your own custom blocks will be highly reusable (juicy)
- [X] Tests become reusable for different platforms (Web, iOS, Android) (juicy)
- [X] Built-in logging for element interactions (nice to have)
- [ ] Built-in reporting for element interactions (nice to have)
- [X] List elements support (must have)
- [X] List custom components support (must have)
- [ ] Widespread web elements wrappers support (nice to have)
- [ ] Dropdown support (nice to have)
- [ ] Multiselect support (nice to have)
- [ ] Autocomplete elements support (nice to have)
- [ ] Tables support (nice to have)

### Why it's convenient should use wrappedelements framework? 
The main drawback of web/mobile ui test automation is slow implementation and it's quite high support cost.
wrapedelements designed to minimise drawbacks. It also and provides some juicy features to do that that other selenium webdriver wrappers frameworks don't have. 

| Features           							                                        | wrappedelements	         | htmlelements             | Selenide                 |
|-------------------------------------------------------------------|--------------------------|--------------------------|--------------------------|
| Supported drivers                         				                    | Chrome, FF, Safari, Edge | Chrome, FF, Safari, Edge | Chrome, FF, Safari, Edge |
| Waiters and timeouts configuration in page object classes		       | Yes            	         | 	No     		               | 	No		                    |
| No web element synchronization code in tests				                  | Yes	          	          | 	No		                    | 	No		                    |
| Dynamic locators support       	      				                        | Yes                      | 	Yes		                   | 	No           	          |
| Automatic web driver recreation (if dead)				                     | Yes	          	          | 	No		                    | 	No	                     |
| Custom components reusable						                                  | Yes	          	          | 	Yes		                   | 	No	       	             |
| Page classes reusable for different platforms (Android, IOS, Web) | Yes	                     | 	No                      | 	No                      |
| Built-in web elements logging 				                                | Yes	                     | 	No                      | 	No                      |