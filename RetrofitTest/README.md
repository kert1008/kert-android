Retrofit + Moshi + OKHttp + Dagger  
  
Use https://randomuser.me/api to get random sample API response  
  
Call API in coroutines in MainActivity to avoid android.os.NetworkOnMainThreadException  
(Can't operate network things in main thread after Android 3)  
  
There is a bug in OKHttp 3.13.1 which caused java.lang.ExceptionInInitializerError  
So I used 3.12.0