package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            for (int i=0; i<100;i++){
//
//                Member member = new Member();
//                member.setUsername("member" + i);
//                member.setAge(i);
//                em.persist(member);
//
//            }

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            //member.setUsername(null);
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.setType(MemberType.USER);
            member2.setTeam(team);
            em.persist(member2);


/*

            //TypeQuery : 반환 타입이 명확할 때 사용
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m where m.id=10", String.class);

            //Query : 반환 타입이 명확하지 않을 때 사용
            Query query3 = em.createQuery("select m.username, m.age from Member m");

            //getResultList : 결과가 하나 이상일 때, 리스트 반환
            //결과가 없으면 빈 리스트 반환
            List<Member> resultList = query1.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }

            //getSingleResult : 결과가 정확히 하나여야함, 단일 객체 반환
            //결과가 없으면 NoResultException
            //결과가 둘 이상이면 NonUniqueResultException
            Member singleResult = query1.getSingleResult();
            System.out.println("singleResult = " + singleResult);

            //파라미터 바인딩
            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("result = " + result.getUsername());

            //조인 쿼리
            List<Team> re = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

            //임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            //스칼라 타입 프로젝션
            em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            List<MemberDTO> resultList1 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = resultList1.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

*/

            em.flush();
            em.clear();

          /*  //페이징
            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result.size() = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }

            //내부 조인
            //String query = "select m from Member m inner join m.team t";
            //외부 조인
            //String query = "select m from Member m left outer join m.team t";
            //세타 조인
            String query = "select m from Member m, Team t where m.username = t.name";

            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();


            //JPQL 타입 표현
            //ENUM은 패키지명 포함해서 적어준다
//            String query1 = "select m.username, 'HELLO', TRUE from Member m " +
//                    "where m.type = jpql.MemberType.ADMIN";
//            List<Object[]> resultList1 = em.createQuery(query1).getResultList();

            //이렇게 적어주면 됨
            String query1 = "select m.username, 'HELLO', TRUE from Member m " +
                   "where m.type = :userType";
            List<Object[]> resultList1 = em.createQuery(query1)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : resultList1) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
            }
*/

            //case식
//            String query =
//                    "select " +
//                    "     case when m.age <= 10 then '학생요금' " +
//                    "          when m.age >= 60 then '경로요금' " +
//                    "          else '일반요금' END " +
//                    "from Member m";

            //COALESCE: 하나씩 조회해서 null이 아니면 반환
            //String query = "select coalesce(m.username,'이름 없는 회원') from Member m";

            //NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
//            String query = "select NULLIF(m.username, '관리자') from Member m";
//
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//            for (String s : result) {
//                System.out.println("s = " + s);
//            }


            String query1 = "select group_concat(m.username) from Member m";
            List<String> result1 = em.createQuery(query1, String.class)
                    .getResultList();
            for (String s : result1) {
                System.out.println("s = " + s);
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }
}
