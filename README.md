# mvc 프레임워크 만들기    
* 현재 jsp와 서블릿을 이용해 구현을 했지만      
  여러 한계점들이 발견되었다.      
  [서블릿과 jsp 한계점](https://github.com/growinghsb/til_blog/blob/main/March/29-3.md)    
  
  해서 앞에 `FrontController`를 두고, 모든 요청이   
  `FrontController`를 거쳐 Controller로 진행될 수   
  있도록 할 것이다.    
  
  이 `FrontController`의 장점은 공통 로직을 앞에서    
  처리할 수 있다는 점, 코드의 중복을 없애고,   
  Controller에 집중 되어 있던 책임을 분산함으로써    
  Controller의 역할에 집중할 수 있게 된다는 것이다.     
  
  결국 `FrontController`만 서블릿이 될 것이고,   
  다른 Controller들은 일반 자바 클래스로 역할을    
  하게 될 것이다.    
  
  또한 여러 중복 코드 역시 `FrontController`에서   
  처리해 코드의 재활용성을 높히고, 유연성을 높힐 것이다.    
***       
* mvc verson 1    
(![mvc verson 1](https://user-images.githubusercontent.com/60066223/112804428-60a47800-90af-11eb-86f1-88f11723baa6.PNG))          




* 위 사진을 보면서 흐름을 설명해보겠다.    
  * 일단 클라이언트로부터 요청이 들어오면    
    어떤 요청이든 `FrontController`로 들어오게    
    되어있다. `FrontController`만 서블릿 객체로    
    생성해 컨테이너가 관리하기 때문.    
    
  * `FrontController`에서 `Map`을 이용해 각 `Controller`들의     
    호출 `Url`을 가지고 키로 가지고 있고, 각 `Url`에 맞는    
    `Controller` 객체를 값으로 가지고 있다.    
    
  * 해서 요청이 들어오면 `request.getRequestUrl()`을 통해       
    해당 요청 `Url`과 맵핑되는 `Controller`를 찾고,      
    해당 `Controller`를 상위 인터페이스인 `ControllerV1` 타입으로    
    받은 뒤 모든 하위 `Controller` 클래스가 재정의 하고 구현한    
    `process()`를 호출하면서 각각의 로직을 수행하게 된다.       
    
  * `Controller` 인터페이스를 통한 다형성을 이용해 요청된    
    Url에 맞게 인터페이스의 구현체인 각 요청을 처리할 수    
    있는 `Controller`들을 대입해 사용하게 된다.    
    
  * 다형성으로 `Controller` 인터페이스 타입으로    
    하위 `Controller`들을 받아서 사용할 수 있다.     
    
  * 조건문을 사용하지 않고, `Map`을 이용해서 객체를 저장해 두는   
    것 역시 기억해둘만 하다. 생각 못했던 부분     

  * 항상 리팩토링 시엔 단계적으로 하나씩 개선해나가야 한다.    
    그냥 한 번에 이것저것 하려고 하면 아무것도 못한다.   
    점진적으로 작은 것 부터 하나하나 해가면서    
    테스트 후 아무 이상 없으면 또 진행하고, 이런식으로     
    점진적으로 해 나가야 한다.      
***      
* mvc verson 2        
![mvc verson 2](https://user-images.githubusercontent.com/60066223/112916839-51b7d700-913c-11eb-8932-6dee08b37862.PNG)    




* 이번 버전의 목표는 view에 대한      
  코드 중복을 걷어내는 것이 목표였다.    
  
* 모든 Controller에      
  ``` java
  String viewPath = "/WEB-INF/views/new-form.jsp";     
  RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);     
  dispatcher.forward(request, response);
  ```
  위 같은 소스가 중복 된다는 것이었다.    
  
* 이를 분리하기 위해서 `MyView` 라는 클래스를 추가했다.    

* 이 `MyView` 클래스는 각 `Controller`에서 상대경로를     
  생성자 매개변수로 받아 초기화 한 뒤      
  `reader()`를 이용해 `jsp`로 포워딩을   
  수행하게 된다. 어쨋든 공통된 작업이기 때문에   
  `FrontController`에서 한 번에 처리 가능했다.    
  
* 그 외 변화는 각 `Controller`에서 `MyView` 객체를 생성해    
  반환하는 것이 변화이다.   
  이유는 결국 `jsp`를 호출하기 위해서이고, `MyView`를    
  `FrontController`에 반환하게 되면서 중복됬던 `jsp`  
  포워딩 작업을 `FrontController`에서 한 번에     
  처리하게 되었다.      
  
* 이 역시 인터페이스를 이용한 다형성으로 구현이 되었다.    
  각 컨트롤러는 `ControllerV2` 인터페이스를 구현하고,     
  해당 메서드는 `MyView`를 반환하게 되어 있다.     
  그리고 이를 구현한 하위 메서드들에서 각자 자기 역할에 맞게    
  내부 구현을 다르게 진행한다.    
  그리고 컴파일 시점이 아닌 런타임 시점에 유동적으로   
  어떤 `Controller`가 선택될 지 결정되고, 메서드 역시    
  해당 `Controller`의 메서드가 실행된다.
***     
* mvc verson 3      
![mvc verson 3](https://user-images.githubusercontent.com/60066223/112926111-ed057800-914d-11eb-8cf1-e7c6e6deede1.PNG)      




* 이번 리팩토링의 주 목적은 각 `Controller`들의    
  `Servlet` 종속성을 제거 하는 것이었다.   
  
* verson 1, verson2 를 거치면서 더이상 `Controller`들에서는    
  서블릿에서 생성한 `request와 response` 객체를 사용하지   
  않도록 구현이 되어 있었기 때문이다.       
  
* 해서 `request`를 사용했던 `Controller`에는     
  미리 필요한 데이터를 `FrontController`에서 작업해     
  `Map` 객체를 넘겨 줌으로써 필요를 대체하고자 한다.    
  
* 그리고 `ViewResolver`를 따로 둬 `Controller`에서 반환하는      
  `View`의 상대경로를 받아 절대경로를 만드는 메서드를 만들어     
  추후에 절대경로가 변경이 생겨도 `ViewResolver`만 변경할 수 있도록    
  진행하고자 한다.    
  
* 그래서 `ModelView` 객체를 만들어 데이터와 `View`를     
  한 번에 전달할 수 있도록 한다.    
  
* 일단 인터페이스 설계를 통해 구현 `Controller`에서     
  `ModelView` 객체를 반환하도록 한다. 매개변수로는     
  `Map` 객체를 받아 데이터를 꺼내 사용할 수 있도록 하고,     
  데이터를 담아 반환해야 할 `Controller`가 `ModelView`객체를    
  통해 데이터를 옮길 수 있도록 반환타입을 `ModelView`로 한다.     
  
* 이렇게 되면 `FrontController`는 요청에서 들어오는    
  데이터를 `Map`에 담아 필요한 `Controller`에 넘겨 줄 수 있고,    
  `Controller` 역시 `ModelView` 객체에 자신이 넘겨 줄   
  `View` 상대경로와, 데이터를 담아 반환하면 된다.    
  
* 그 후 `ViewResolver`를 통해 절대경로를 반환받게 되고      
  랜더링을 진행하는데 `jsp`에서 데이터를 사용하려면    
  `setAttribute()` 작업을 진행해 줘야 한다.    
  
* 해서 랜더링 작업 시 `map`에 있는 데이터를 다 조회해     
  `setAttribute()`에 넣고, `forword()` 하면 된다.    
***
* mvc verson 4     
![mvc verson 4](https://user-images.githubusercontent.com/60066223/112932711-5ccd3000-9159-11eb-9be8-732d4408e31b.PNG)            




* 이번 리팩토링은 기존의 기능을 그대로 유지하면서         
  `Controller`에서 `FrontController`에게 `ModelView 객체`가 아닌         
  그냥 `View` 상대경로만, 즉 문자열만 리턴하게 바꿨다.             
  
* 이유는 어짜피 `ModelView 객체`에 상대경로만 담아서        
  객체를 생성해 넘기기 때문에 데이터를 전달하는 `Model 객체(Map)`는                   
  매개변수로 전달하고, 넣을 값이 있으면 `Model 객체(Map)`에                     
  put() 해서 넣는다. 그렇게 되면 `call-by-reference`로          
  객체의 참조값을 넘기기 때문에 (여기서는 Map)          
  `FrontController`의 `Model 객체(Map)`에 데이터가 담긴다.            
 
* 그리고 해당 `Controller`가 반환할 상대경로를 넘기면    
  `FrontController`에서는 `ViewResolver`를 통해   
  절대경로로 만들어 최종적으로 `MyView`에 넘기게 되고,   
  이후 mvc verson 3과 동일한 과정을 거쳐     
  jsp 로 `forword()` 된다.
***
* 여기까지 진행하면서 항상 점진적으로 발전시켜 가야 함을       
  깨닫게 되었다. 가장 탄탄한 밑바닥에서 구조를 잡고,       
  테스트 하고, 디테일을 덧붙여 가는 방식으로 만들어가야 한다.        
  한 번에 완성품으로 갈 순 없다. 아니 애초에 완성이란          
  존재하지 않는다. 변하지 않으면 도태되는 것이 소프트웨어다.         
  그렇기 때문에 점진적으로 조금씩 발전시켜 나가는 것을       
  당연히 여기고, 작은거라도 일단 단단하게 하나씩 만들어 가자.      
***
* mvc verson 5      
![mvc verson 5](https://user-images.githubusercontent.com/60066223/112945112-cefc3f80-916e-11eb-8207-9b6ce31dc3d8.PNG)         




* 일단 놀랍다.. Adapter 패턴을 적용했는데 너무 아름답다.             
  정리하고, 여기에 더 추가해서 인터페이스로 바꿀 수 있는 것들은         
  한 번 바꿔보자.       
  [어댑터 패턴 정리](https://github.com/growinghsb/til_blog/blob/main/March/30-1.md)        

* verson 4 까지는 하나의 `Controller` 타입만 사용할 수 있었다.   
  이유는 각 `Controller` 마다 반환 타입이 서로 다르기 때문에   
  인터페이스를 공유하지 못했다.    
  
* 하지만 `Adapter` 패턴을 사용해 각 `Controller`를 하나로 통일할 수    
  있게 되었다. `Adapter`를 각 `Controller`마다 만들고    
  `FrontController`에서는 `Adapter`를 통해 `Controller`의   
  로직을 수행하고, 반환 타입으로 동일한 타입의 객체를   
  반환 받는다. 
  
* 이게 가능한 이유는 `Controller`들의 반환 타입은 다르지만   
  각 `Controller`에 붙은 `Adapter`들의 반환타입은 같고,    
  `FrontController`는 `Adapter`를 통해 `Controller`의 로직을      
  수행하기 때문이다.    
  
* 이로써 v3, v4 `Controller`를 모두 요청에 따라     
  자유롭게 사용할 수 있게 되었다.    
  
* 어쨋든 `Controller`는 동일한 작업을 수행한다.    
  하지만 수행하는 방법이 각자 달랐고,   
  `FrontController`가 직접 `Controller`를 호출 했을 땐     
  이러한 부분 때문에 v3 전용, v4 전용 `FrontController`가    
  필요했다.    
  
* 하지만 `FrontController`가 `Adapter`라는 추상화에 의존하고,    
  각 `Adapter`는 `FrontController`에 일관된 구현을 보장 함으로써    
  서로 구현이 다른 `Controller`임에도 불구하고,    
  요청에 따라 자유롭게 사용 가능하다.    
  
* 또한 여기서 중요한 것은 `Controller`가 추가되고,     
  `Adapter`가 추가되도, `FrontController`는    
  바뀌지 않는다는 것이다.     
  
* 그 이유는 간단하다. `FrontController`가    
  `Adapter`라는 추상화에 의존하고 있기 때문이다.    
  
* `Adapter`는 각 `Controller`에 붙어 `FrontController`와    
  `Controller` 사이에서 규격을 통일 함으로써(반환타입)     
  역할을 다하고 있다.    
***    
* 현재 V1 Controller도 Adapter를 만들어서     
  FrontControllerV5에서 처리할 수 있도록 했다.     
  이 역시 너무 간편하게 Adapter만 구현하면 가능했다.    
  이렇게 추상화에만 의존하면 확장이 너무 편하게 가능하다.        
  
* V2 Controller 역시 Adapter만 만들면    
  바로 확장이 가능했다. 이제 AdapterMapping을     
  정리해 봐야겠다. 현재 FrontController에 이렇게 되어있다.    
  ![FrontController init](https://user-images.githubusercontent.com/60066223/113225751-578ef300-92c9-11eb-8364-6aa4b3978f0f.PNG)      
  
  
  
  
* 이건 어떻게 정리하는게 좋을까? 일단 url이 들어오면    
  해당 url에 맞는 Controller를 찾아서 반환하는 것이다.     
  어노테이션 방식이면 각 컨트롤러마다 객체를 생성할   
  필요 없이 컨트롤러 객체 하나에 메소드 단위로      
  맵핑이 가능한데 이 방식은 인터페이스를 각각 만들어서    
  구현한거라 어쩔 수 없다. 이게 어떻게 구현되어 있는지    
  스프링을 뜯어 봐야겠다.     
  
* 뜯어보기 전에 좀 생각을 해보자.    
  객체지향 설계에서 가장 중요한 것은    
  확장에는 열려있고, 수정에는 닫혀 있는 구조이다.    
  
* 확장을 한다면 map에다가 put을 하는 것이고,    
  추가된 url에 맵핑 된 컨트롤러를 추가하는 것이다.    
  
* 그리고 이에 의존하고 있는 클라이언트인 FrontController는    
  변경이 없어야 한다. 확장에는 열려 있지만 수정에는 닫혀있기    
  때문이다. 그러려면 이 맵핑만 따로 처리하는 클래스를 만들어    
  분리하면 된다. 그리고 요청을 넘기면서 컨트롤러를 달라고 요청하게    
  하면 된다. 그렇게 되면 FrontController는 클라이언트 입장에서    
  합성을 통한 요청만을 처리하게 된다.        
  
* 일단 분리하고, 추상화에 의존할 수 있도록 리팩토링 해보자.     
***      
* 현재 AdapterMapping을 분리했다. 확장 시 AdapterMapping에만    
  추가해주면 되고, 클라이언트인 FrontController는 변경할 게 없다.    
  다만 맘에 걸리는건 구체화에 의존한다는 점이지만    
  AdapterMapping 특성상 큰 변화가 있진 않을 거 같다.    
  변화라고 하면 컨트롤러가 추가되는거? 하지만 이 역시    
  클라이언트인 FrontController에는 영향을 미치지 않는다.    
  
* 추상화 하는 방법을 모르겠다. 경험이 부족한건지, 공부가 부족한건지       
  일단 다 분리하고, 어노테이션컨트롤러까지 구현해보고 한 번더    
  리팩토링을 해보자.     
***  
