package com.example.hikaru_tsubuki.repository;


import com.example.hikaru_tsubuki.repository.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    //デフォルトの日付のみ
    public List<Task> findByLimitDateBetweenOrderByLimitDateAsc(Date startDate, Date endDate);
    //すべての絞込条件が有り
    public List<Task> findByLimitDateBetweenAndContentAndStatusOrderByLimitDateAsc(Date startDate, Date endDate, String content, Integer status);
    //日付とステータスが引数の場合
    public List<Task> findByLimitDateBetweenAndStatusOrderByLimitDateAsc(Date startDate, Date endDate, Integer status);
    //日付とキーワードが引数の場合
    public List<Task> findByLimitDateBetweenAndContentOrderByLimitDateAsc(Date startDate, Date endDate, String content);

    @Modifying //UpdateやDeleteを実行する宣言。これはつけたほうがいい。
    @Transactional //トランザクション内で実行する宣言。UpdateやDeleteの時につける。途中で失敗したときにロールバックできるように。
    //@Query=　JPQLの指定。UPDATE Task t = Taskエンティティをtという名前で使う。SET t.status = :status,= statusフィ―ルドうぃ;statusパラメーターの値で更新
    //t.updatedDate = CURRENT_TIMESTAMP =　updateDateで現在の日時に更新　WHERE t.id = :id = idが一致するレコードを対象にする。
    @Query("UPDATE Task t SET t.status = :status, t.updatedDate = CURRENT_TIMESTAMP WHERE t.id = :id")
    //@Param("id"),@Param("status") JPQLのidとstatusに対応。Serviceの引数を@Paramで使えるようにしている。
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
