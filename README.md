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
