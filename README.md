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
