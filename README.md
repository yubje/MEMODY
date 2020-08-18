# 📖 Memody Project 

![version](https://img.shields.io/badge/version-0.0.1-orange?)![vue](https://img.shields.io/badge/vue-3.0.0-blue?logo=Vue.js)![spring](https://img.shields.io/badge/spring-4.0.0-yellow?logo=spring)![spring-boot](https://img.shields.io/badge/springboot-4.0.0-yellow?logo=spring)[![mysql](https://jaywcjlove.github.io/sb/ico/mysql.svg)](http://www.mysql.com/)![html](https://img.shields.io/badge/html-html5-red?logo=html5)![css](https://img.shields.io/badge/css-css3-red?logo=css3)![javascript](https://img.shields.io/badge/javascript-es6-yellowgreen?logo=javascript)![aws-s3](https://img.shields.io/badge/aws-s3-blue)![aws-ec2](https://img.shields.io/badge/aws-ec2-blue)

### 🏠 [Memody Homepage](http://i3a306.p.ssafy.io/)

<hr>

### 📂 Contents

- [Project 소개](#%EF%B8%8F-shared-your-blog)
- [기술 스택](#-기술-스택)
- [사용 기술](#-사용-기술)

- [Commit | Merge | Pull](#-commit-merge-pull) 
- [역할분담](#-역할-분담)
- [Git Branch 전략](#-git-branch-전략)
- [Jira](#-jira)
- [Code convention](#-code-convention)
- [와이어프레임](#-와이어프레임)

<hr>

### 🖥️ Shared Your Blog

- 다양한 분야의 블로그를 조회할 수 있고, 직접 블로그를 만들어 운영할 수 있다.

- 개인뿐만 아니라 다수가 블로그를 관리할 수 있다.

- 블로그 내용을 공유할 수 있는 생태계 구축
  - 타 블로그의 게시글을 Fork하여 내 블로그로 가져올 수 있다.
  - 여기저기 퍼져있는 다양한 정보들을 내 블로그로 모을 수 있다.

- 랭킹 시스템 도입
  - 블로그의 조회수, 게시글이 Fork된 횟수가 많아질수록 영향력있는 블로거가 된다.
  - 랭킹 시스템을 통해 유명한 블로거가 되기 위해 다양하고 유익한 정보들을 공유할 수 있는 생태계가 형성된다.

<hr>

### 📃 기술 스택

![image](/uploads/9ba20fb403527634ae5d94eb31dc6148/image.png)

**BACKEND**

1. Programming Languages : [ Java 8 ] 
2. Frameworks : [ Spring ] 
   - Tool : [ Spring boot ]
3. SQL data storage : [ MySQL ]
4. Key-value storage : [ Redis ]
5. Web Server : [ Nginx ]
6. Web application server : [ Apache Tomcat ]
7. Hosting : [ AWS ]

**FRONTEND**

1. Programming Languages : [ JavaScript, HTML5, CSS3 ]
2. JavaScript Framework : [ Vue.js ]



<hr>

### 📃 사용 기술

**Spring boot** : Memody Project의 전반적인 기능 Rest Controller 구현

**Spring Security + JWT** : 로그인한 사용자만 서비스를 사용가능 하도록(filter), Token을 계속 검사해서 유효한 회원인지 확인

**Spring Data JPA**: JpaRepository를 상속받아 쿼리 작성을 하지 않고 DB Table의 객체로 접근하게 하여 기능들을 구현

**XSS Filter** : 네이버에서 제공하는 lucy-xss-servlet-filter 를 이용하여 서버로 보내는 입력 값에 자바스크립트를 보내서

다른 사용자에게 자신이 만든 스크립트를 실행시켜서 사용자의 정보를 빼내는 XSS 공격에 대비하도록 구현 

**MySql** : 사용자 및 블로그, 게시글 등 Memody PJT에서 필요한 Data를 저장

**Redis** :  Key와 value가 매핑된 단순한 맵 데이터 저장소로서 데이터를 레디스에 쉽고 편하게 읽고 쓸 수 있게 하였습니다. Memody PJT에서는 로그아웃한 사용자의 토큰을 만료시간까지 저장해두는 블랙리스트를 구성하였고, 이메일 인증코드를 사용하는 기능에서 사용자 이메일과 인증 코드를 redisTemplate을 사용하여 key, value로 저장하고, 인증 코드 사용이 완료되면 삭제를 하는 기능을 구현 

**AWS** : EC2 서비스를 이용하여 Ubuntu 서버를 구축(호스팅), S3 서비스를 이용하여 파일을 업로드 할 수 있도록 구축

**Nginx** : 웹서버를 구축



<hr>



### 📃 Commit  | Merge | Pull

- 시간

  - AM 10:00  | PM 16:00 에 

  - 일주일에 한번은 `develop`에 MR
  - `Front` / `Back` 에 각자 `Feature`를 MR,  `develop`  Pull 하여 최신화 

- Commit Message Rule

  `{JIRA issue ID} | {진행상황} | {기능 명}`

  ex) `S03P11A305-1 | Done | 회원가입`

  ex) `S03P11A305-2 | In Progress| 회원정보 수정`

  `[마무리] | {날짜 }| README.md`

  - ex) `[마무리] | 2020.07.21 | README.md`

  `[MR] | {날짜} | '{소스브랜치}' into '{타겟브랜치}'`

  

  **진행상황** 

  - Todo
  - In Progress
  - Done

  

### 📃 역할 분담

- PM

  🕵‍♂ **민경**

- Frontend

  👩‍💻 **유빈** 👨‍💻 재영 👩‍💻 유진

- Backend

  👨‍💻 **형택** 👩‍💻 민경



### 📃 Git Branch 전략

- Master

  - Develop

    - Front

      - FeatureFront/doc
      - FeatureFront/epic Name

    - Back

      - FeatureBack/doc
      - FeatureBack/epic Name
            

### 📃 Jira

##### 프로젝트 관리 도구로 Jira를 사용, Issue를 등록하여 프로젝트를 진행

- Epic : 전체적인 큰 기능들을 Epic으로 구성
  - Ex) Front / User Function (회원 관리) , Back / Blog Function (블로그)

- Story : Epic과 연결하고 Epic에 관련된 기능 구현을 위주로 구성
  - Ex) Front / Main Page , Back / Blog CRUD , Back / Spring Security
- Bug : 테스트 과정에서 발견된 bug를 등록
  - Ex) Back / JWT Token update



### 📃 Code convention

- [JavaScript](https://github.com/airbnb/javascript)

  > 자세한 내용은 공식문서 참고

  ``` javascript
  exports default {
    created() {
      }
    },
    methods: {
      setInputValue(state) {
  	  console.log('example')
      },	
    },
  }
  ```

  **Naming Convention**

  > 기본적으로 Full Name으로 씁시당

  ```javascript
  ex) res => response, err => error (keyword 걸리는건 제외입니다.)
  
  ```

  

  **Whitespace** [띄어쓰기 보세요~~]

  - Use soft tabs set to 2 spaces.

    ```javascript
    // bad
    function () {
    ∙∙∙∙var name;
    }
    
    // bad
    function () {
    ∙var name;
    }
    
    // good
    function () {
    ∙∙var name;
    }
    ```

  - Place 1 space before the leading brace.

    ```javascript
    // bad
    function test(){
      console.log('test');
    }
    
    // good
    function test() {
      console.log('test');
    }
    
    // bad
    dog.set('attr',{
      age: '1 year',
      breed: 'Bernese Mountain Dog'
    });
    
    // good
    dog.set('attr', {
      age: '1 year',
      breed: 'Bernese Mountain Dog'
    });
    ```

  - Place 1 space before the opening parenthesis in control statements (`if`, `while` etc.). Place no space before the argument list in function calls and declarations.

    ```javascript
    // bad
    if(isJedi) {
      fight ();
    }
    
    // good  띄어쓰기 보세요 ~~
    if (isJedi) {
      fight();
    }
    ```

  - Set off operators with spaces.

    ```javascript
    // bad
    var x=y+5;
    
    // good
    var x = y + 5;
    ```

  - Leave a blank line after blocks and before the next statement

    ```javascript
    // bad
    if (foo) {
      return bar;
    }
    return baz;
    
    // good
    if (foo) {
      return bar;
    }
    
    return baz;
    
    // bad
    var obj = {
      foo: function () {
      },
      bar: function () {
      }
    };
    return obj;
    
    // good
    var obj = {
      foo: function () {
      },
    
      bar: function () {
      }
    };
    
    return obj;
    ```

  **Commas**

  - Leading commas: **Nope.**

    ```javascript
    // bad
    var story = [
        once
      , upon
      , aTime
    ];
    
    // good
    var story = [
      once,
      upon,
      aTime
    ];
    ```

  **Naming Convention**

  - Use PascalCase when naming constructors or classes.

    ```javascript
    // bad
    function user(options) {
      this.name = options.name;
    }
    
    var bad = new user({
      name: 'nope'
    });
    
    // good
    function User(options) {
      this.name = options.name;
    }
    
    var good = new User({
      name: 'yup'
    });
    ```

    

  ```javascript
  // Keywords : 
  break,do,instanceof,typeof,case,else,new,var,catch,finally,return,void,continue,for,switch,while,
  debugger,function,this,with,default,if,throw,delete,in,try
  
  // Key값을 키워드로 잡지 말기
  // good
  var superman = {
    defaults: { clark: 'kent' },
    hidden: true
  };
  ```

  **Strings**

  ```javascript
  // String 쓸 때 '' 쓰기 	
  // bad
  var name = "Bob Parr";
  
  // good
  var name = 'Bob Parr';
  ```

  **Properties**

  ```javascript
  var luke = {
    jedi: true,
    age: 28
  };
  
  // bad
  var isJedi = luke['jedi'];
  
  // good
  var isJedi = luke.jedi;
  
  //Use subscript notation [] when accessing properties with a variable.
  var luke = {
    jedi: true,
    age: 28
  };
  
  function getProp(prop) {
    return luke[prop];
  }
  
  var isJedi = getProp('jedi');
  ```

  

- Use `===` and `!==` over `==` and `!=`.

  - **Objects** evaluate to **true**

  - **Undefined** evaluates to **false**

  - **Null** evaluates to **false**

  - **Booleans** evaluate to **the value of the boolean**

  - **Numbers** evaluate to **false** if **+0, -0, or NaN**, otherwise **true**

  - **Strings** evaluate to **false** if an empty string `''`, otherwise **true**

  - use shortcuts

  - ```javascript
    // bad
    if (name !== '') {
      // ...stuff...
    }
    
    // good
    if (name) {
      // ...stuff...
    }
    
    // bad
    if (collection.length > 0) {
      // ...stuff...
    }
    
    // good
    if (collection.length) {
      // ...stuff...
    }
    ```

  **if elif else**

  ```javascript
  // bad
  if (test) {
    thing1();
    thing2();
  }
  else {
    thing3();
  }
  
  // good
  if (test) {
    thing1();
    thing2();
  } else 
    thing3();
  }
  ```


#### JAVA

- [Java 컨벤션](https://myeonguni.tistory.com/m/1596)

1) 문서 주석

- 클래스 설명 주석

  - import 문 다음에 기술한다.

  - 블록주석을 사용한다.

  - 각 라인은 *로 시작한다.

  - 해당 클래스에 대한 기능과 용도 기술을 작성한다.

  - <pre>...</pre> 내용은 수정하지 않는다.

  ```java
  /* 
   * 클래스 이름
   * <pre>
   * <b> History:</b>
   *		작성자, 작성 버전, 작성 일자, (최초 작성)
   * </pre>
   *
   * @author 작성자 이름
   * @version 1.0, 수정 일자, 내용
   * @see None
   */
  ```

- 멤버 함수 주석

  - 함수의 상단에 위치한다.
  - 블록 주석을 사용한다.
  - 메소드 기능 설명은 한 두줄로 간결하게 기술한다.
  - 메소드의 파라미터를 type명과 변수명을 적고 간략하게 설명한다.

  ```java
  /**
   * 로그인 - userId와 userPw로 회원 확인 
   *
   * @param int userId - 사용자 아이디
   * @param int uwerPw - 사용자 패스워드
   * @return 로그인 성공 여부, 로그인 성공시 true return
   * @ exception 예외사항
   */
  ```

- 선언

  -  한 줄에 하나의 선언문 사용

  ```java
  int			id; 		// 사용자 아이디
  int 		pw; 		// 사용자 비밀번호
  Object 		time		// 등록 시간   
  ```

2) 들여쓰기

- 1번의 탭 또는 4번의 공백

3) 줄 나누기

- 하나의 식이 한 줄에 들어가지 않을 때

  - 콤마 후에 두줄로 나눈다.

  - 연산자 앞에서 두줄로 나눈다.

  - 들여쓰기는 8개의 빈 칸 원칙을 사용한다.

  - ```
    if ((condition1 && condition2)
    		|| (condition3 && condition4)
            ||!(condition5 && condition6)) {
        doSomethingAboutIt();
    }
    ```

4) 괄호

- 여는 괄호 다음과 닫는 괄호 이전에는 공백이 없도록 한다.

5)  명명 규칙

- Packages
  - 패키지 이름의 최상위 레벨은 소문자, 가장 높은 레벨의 도메인 이름중 하나로 설정한다.
  - 패키지 이름의 나머지 부분은 프로젝트 명으로 설정한다.
  - 해당 프로젝트에서는 com.web.blog 로 설정한다.
- Classes
  - 클래스 이름은 명사, 복합 단어일 경우 각 단어의 첫 글자는 대문자로 설정한다.
  - 첫 글자는 **대문자로 시작**한다.
- Interfaces
  - 클래스 이름과 같은 대문자 사용 규칙을 적용한다.
- Methods
  - 메소드의 이름은 동사, 복합 단어일 경우 첫 단어는 **소문자로 시작**하고, 그이후에 나오는 단어의 첫 문자는 대문자로 사용한다.
    - run();
    - runMethod();
- Variables
  - 변수 이름의 첫 번째 문자는 **소문자로 시작**하고, 각 내부 단어의 첫 번째 문자는 대문자로 시작한다.
  - 그 변수의 사용 의도를 알아낼 수 있도록 의미있게 설정한다.
- Constants
  - 클래스 상수로 선언된 변수들의 이름은 모두 **대문자로 사용**하고 각각의 단어는 언더바(_)로 분리한다.
    - static final int MIN_WIDTH = 4;



----

### Mysql 데이터베이스 규칙

1) 공통

- 소문자를 사용한다.
- snake case를 사용한다.

2) Table

- 복수형을 사용한다.
- 이름을 구성하는 각각의 단어를 underbar로 연결하여 snake case를 사용한다.
  - vip_member
- 교차 테이블의 이름은 "\_and_"로 연결한다
  - articles_and_movies

3) Column

- auto increment 속성의 PK를 대리키로 사용하는 경우, "테이블 이름의 단수형"_id 의 규칙으로 명명한다.

- 이름을 구성하는 각각의 단어를 **underscore** 로 연결하는 **snake case** 를 사용한다.

- foreign key 컬럼은 부모 테이블의 primary key 컬럼 이름을 그대로 사용한다.

  - self 참조인 경우, primary key 컬럼 이름 앞에 적절한 접두어를 사용한다.
  - 같은 primary key 컬럼을 자식 테이블에서 2번 이상 참조하는 경우, primary key 컬럼 이름 앞에 적절한 접두어를 사용한다.

- boolean 유형의 컬럼이면 "_flag" 접미어를 사용한다.

- date, datetime 유형의 컬럼이면 "_date" 접미어를 사용한다.

  ```mysql
  article_id, movie_id : "테이블 이름의 단수형" + "_id"
  complete_flag : boolean 유형의 컬럼
  issue_date : 날짜 유형의 컬럼
  ```

4) Foreign Key

- 이름을 구성하는 각각의 단어를 **hyphen** 으로 연결하는 **snake case** 를 사용한다.

- "fk"-"부모 테이블 이름"-"자식 테이블 이름"

  - 같은 부모-자식 테이블에 2개 이상의 foreign key가 있는 경우, numbering합니다.

    ```mysql
    fk-movies-articles : article 테이블이 movie 테이블을 참조
    fk-admins-notices-1 / fk-admins-notices-2 : notices 테이블이 admins 테이블을 2회 이상 참조하여 numbering
    ```

    

5) Index

- 이름을 구성하는 각각의 단어를 **hyphen** 으로 연결하는 **snake case** 를 사용한다.
- 접두어
  - unique index : uix
  - spatial index : six
  - index : nix
- "접두어"-"테이블 이름"-"컬럼 이름"-"컬럼 이름"

```mysql
uix-accounts-login_email
```



### 📃 와이어프레임

- [발사믹](https://lab.ssafy.com/s03-webmobile1-sub3/s03p13a306/tree/develop/doc/Wireframe)

