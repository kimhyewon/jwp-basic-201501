#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 서블릿컨테이너는 사용자의 개입없이 초기화를 진행한다.
초기화는 두 군데에서 진행된다. 한 군데는 DispatcherServlet이고, 다른 한 곳은 ContextLoaderListener 클래스 이다.

DispatcherServlet에서 init 메소드가 실행된다. 이 메소드가 실행되는 이유는 어노테이션 설정에서 loadOnStartup = 1 라는 부분을 통해 서버가 로드되자마자 실행되도록 했기 때문이다. DispatcherServlet 클래스의 init 메소드에서는 RequestMapping() 객체를 만들고, initMapping() 메소드를 실행한다. initMapping() 메소드에서는 mappings.put("/list.next", new ListController()); 이런 식으로 컨트롤러 역할을 하는 서블릿과 url? 을 매핑한다.

두 번째로 ContextLoaderListener 클래스에서도 초기화가 진행된다. @WebListener 라는 이 어노테이션을 통해서 자동으로 이 클래스가 불려져서 실행된다. 이 클래스에서는 데이터베이스 관련 초기화 작업이 이루어 진다.



#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 서버는 사용자가 날린 url을 분석하여 어떤 서블릿을 요청헀는지 알아내는데, 8080포트로 사용자가 접속을하면 서버는 이 사용자의 요청을 처리하기 위해 일을 한다. WebserverLauncher에서 8080포트로 접속되도록 웹 서버를 실행시키고 웹 자원을 관리하는 디렉토리를 webapp으로 설정한다. webapp에서 보여주는 디폴트 페이지는 index.jsp이므로 index.jsp를 실행시킨다. index.jsp에는 /list.next로 리다이렉트를 시킨다. RequestMapping 이라는 클래스에서 initMapping()를 통해 /list.next는 ListController와 매핑되어있다. ListController에서는 ModelAndView mav = jstlView("list.jsp”); 라는 부분을 통해서 list.jsp를 띄워준다.

#### 7. ListController와 ShowController가 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* ListController와 ShowController는 한번만 생성되는데, 멤버변수는 여러 멀티 쓰레드 상황에서 공유가 되기때문에 문제가 발생할 수 있다. 그래서 멤버변수를 지역변수로 바꿔줘야 한다. 그래서 스레드가 생셩될때마다 변수를 만들어주도록 하여 멀티쓰레드 상황에서의 문제를 해결한다

