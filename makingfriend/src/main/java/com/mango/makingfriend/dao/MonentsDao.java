package com.mango.makingfriend.dao;

import java.util.List;

import com.mango.makingfriend.model.Monents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MonentsDao extends JpaRepository<Monents,Long >  {


	@Query("from Monents m order by m.publishTime desc")
	Page<Monents> getMonentInfo(Pageable pageable);

	@Query("from Monents m where m.user.hxid=?1 order by m.publishTime desc")
    Page<Monents> getOneMonentInfo(int UserId,Pageable pageable);

	// Monents save(Monents monents);


//	public int deleteMonent(String mid);
    @Query("from Monents m where m.mid=?1")
	 List<Monents> findOneMonentInfo(int mid);

    @Query("select m.imageStr from Monents m where  m.mid=?1")
	 String getMonentImage(int mid);
	

}
