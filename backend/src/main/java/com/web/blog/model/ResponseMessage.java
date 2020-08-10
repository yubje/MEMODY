package com.web.blog.model;
public class ResponseMessage {

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String LOGOUT_SUCCESS = "로그아웃 성공";
    public static final String LOGOUT_FAIL = "로그아웃 실패";
    
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    
    public static final String CREATE_CODE = "인증번호 생성";
    public static final String RESET_PWD = "비밀번호 재설정 성공";
    public static final String FAIL_RESET_PWD = "비밀번호 재설정 실패";
    public static final String SEARCH_NICKNAME_NONE = "닉네임 사용 가능";
    public static final String SEARCH_NICKNAME_EXIST = "이미 존재하는 닉네임입니다.";
    
    
    public static final String ALREADY_USER = "이미 존재하는 Email입니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String FAIL_CREATE_USER = "회원 가입 실패";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String FAIL_UPDATE_USER = "회원 정보 수정 실패";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    
    // Blog
    public static final String CREATE_BLOG_SUCCESS = "블로그 생성 성공";
    public static final String CREATE_BLOG_FAIL = "블로그 생성 실패";
    public static final String SEARCH_MYBLOG_NONE = "내 블로그가 없습니다.";
    public static final String SEARCH_MYBLOG_SUCCESS = "내 블로그가 조회 성공.";
    public static final String SEARCH_BLOG_SUCCESS = "블로그 조회 성공";
    public static final String SEARCH_BLOG_FAIL = "블로그 조회 실패";
    public static final String UPDATE_BLOG_SUCCESS = "블로그 수정 성공";
    public static final String UPDATE_BLOG_FAIL = "블로그 수정 실패";
    public static final String DELETE_BLOG_SUCCESS = "블로그 삭제 성공";
    public static final String DELETE_BLOG_FAIL = "블로그 삭제 실패";
    public static final String BLOG_MEMBER_SUCCESS = "블로그 멤버 조회 성공";
    public static final String BLOG_MEMBER_FAIL = "블로그 멤버 조회  실패";
    public static final String INVITE_MEMBER_SUCCESS = "블로그 멤버 추가 성공";
    public static final String INVITE_MEMBER_FAIL = "블로그 멤버 추가 실패";
    public static final String DELETE_MEMBER_SUCCESS = "블로그 멤버 삭제 성공";
    public static final String DELETE_MEMBER_FAIL = "블로그 멤버 삭제 실패";
    public static final String FOLLOW_BLOG_SUCCESS = "블로그 팔로우 성공";
    public static final String UNFOLLOW_BLOG_SUCCESS = "블로그 팔로우 취소 성공";
    public static final String SEARCH_BLOGFOLLOW_SUCCESS = "블로그 팔로우 조회 성공";
    
    
    public static final String RECOMMEND_BLOG_SUCCESS = "추천 블로그 목록 조회 성공";
    public static final String MAIN_SUCCESS = "메인 페이지 성공";
    
    
    
    // Post
    public static final String CREATE_POST_SUCCESS = "성공적으로 게시글을 작성하였습니다.";
    public static final String SAVE_POST_SUCCESS = "임시저장 되었습니다.";
    public static final String CREATE_POST_FAIL = "게시글 작성 실패";
    public static final String SEARCH_ALLPOST_NONE = "게시글이 없습니다.";
    public static final String SEARCH_ALLPOST_SUCCESS = "전체 게시글 목록 조회 성공";
    public static final String SEARCH_ALLSAVEPOST_NONE = "임시저장한 게시글이 없습니다.";
    public static final String SEARCH_ALLSAVEPOST_SUCCESS = "전체 임시저장 게시글 목록 조회 성공";
    public static final String SEARCH_POST_SUCCESS = "게시글 상세 조회 성공";
    public static final String SEARCH_POST_FAIL = "해당 게시글이 존재하지 않습니다.";
    public static final String UPDATE_POST_SUCCESS = "게시글 수정 성공";
    public static final String DELETE_POST_SUCCESS = "게시글 삭제 성공";
    public static final String LIKE_POST_SUCCESS = "게시글 좋아요 성공";
    public static final String UNLIKE_POST_SUCCESS = "게시글 좋아요 취소 성공";
    public static final String SEARCH_POSTLIKE_SUCCESS = "게시글 좋아요 조회 성공";
    
    
    // Comments
    public static final String CREATE_COMMENT_SUCCESS = "성공적으로 댓글을 작성하였습니다.";
    public static final String CREATE_COMMENT_FAIL = "댓글 작성 실패";
    public static final String SEARCH_ALLCOMMENT_NONE = "게시글에 댓글이 없습니다.";
    public static final String SEARCH_ALLCOMMENT_SUCCESS = "게시글 전체 댓글 조회 성공";
    public static final String SEARCH_COMMENT_NONE = "해당 댓글이 없습니다.";
    public static final String SEARCH_COMMENT_SUCCESS = "댓글 조회 성공";
    public static final String UPDATE_COMMENT_SUCCESS = "댓글 수정 성공";
    public static final String DELETE_COMMENT_SUCCESS = "댓글 삭제 성공";
    
    // Category
    public static final String CREATE_CATEGORY_SUCCESS = "카테고리 추가 성공";
    public static final String CREATE_CATEGORY_FAIL = "카테고리 추가 실패";
    public static final String UPDATE_CATEGORY_SUCCESS = "카테고리 수정 성공";
    public static final String UPDATE_CATEGORY_FAIL = "카테고리 수정 실패";
    public static final String DELETE_CATEGORY_SUCCESS = "카테고리 삭제 성공";
    public static final String DELETE_CATEGORY_FAIL = "카테고리 삭제 실패";
    public static final String SEARCH_CATEGORY_SUCCESS = "카테고리 조회 성공";
    public static final String SEARCH_CATEGORY_FAIL = "카테고리 조회 실패";
    public static final String SEARCH_POSTBYCATEGORY_SUCCESS = "카테고리 내 전체 게시글 조회 성공";
    public static final String SEARCH_POSTBYCATEGORY_FAIL = "카테고리 내 전체 게시글 조회 실패";
    
    public static final String AUTHORIZED = "인증 성공";
    public static final String UNAUTHORIZED = "인증 실패";
    public static final String FORBIDDEN = "인가 실패";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String SERVICE_UNAVAILABLE = "현재 서비스를 사용하실 수 없습니다. 잠시후 다시 시도해 주세요.";

    public static final String DB_ERROR = "데이터베이스 에러";
}