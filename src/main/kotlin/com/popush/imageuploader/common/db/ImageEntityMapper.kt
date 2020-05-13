package com.popush.imageuploader.common.db

import com.popush.imageuploader.common.model.ImageEntityCondition
import lombok.NonNull
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface ImageEntityMapper {
    @Insert("""
        INSERT INTO `image`
        (
            `key`,
            `thumbnail_key`,
            `enable`
        )
        VALUES
        (
            #{key},
            #{thumbnailKey},
            #{enable}
        )
    """)
    fun insert(@NonNull imageEntity: ImageEntity): Int

    @Update("""
        <script>
            UPDATE `image`
            <set>
              <if test="data.key != null">`key`=#{data.key},</if>
              <if test="data.enable != null">`enable`=#{data.enable},</if>
              <if test="data.thumbnailKey != null">`thumbnail_key`=#{data.thumbnailKey},</if>
            </set>
            <where>
                <if test="condition.id != null">
                    AND `image_id` = #{condition.id}
                </if>
                <if test="condition.key != null">
                    AND `key` = #{condition.key}
                </if>
                <if test="condition.enable != null">
                    AND `enable` = #{condition.enable}
                </if>
            </where>
        </script>
    """)
    fun update(
            @Param("condition") imageEntityCondition: ImageEntityCondition,
            @Param("data") imageEntity: ImageEntity
    ): Int

    @Delete("""
        <script>
            DELETE FROM `image`
            <where>
                `key` = #{key}
            </where>
        </script>
    """)
    fun deleteByKey(@NonNull key: String): Int

    @Delete("""
        <script>
            DELETE FROM `image`
            <where>
                `image_id` = #{id}
            </where>
        </script>
    """)
    fun deleteById(@NonNull id: Long): Int


    @Results(value = [
        Result(property = "imageId", column = "image_id"),
        Result(property = "thumbnailKey", column = "thumbnail_key"),
        Result(property = "createdAt", column = "created_at"),
        Result(property = "updatedAt", column = "updated_at")
    ])
    @Select("""
        <script>
            SELECT *
            FROM `image`
            <where>
                <if test="condition.id != null">
                    AND `image_id` = #{condition.id}
                </if>
                <if test="condition.key != null">
                    AND `key` = #{condition.key}
                </if>
                <if test="condition.enable != null">
                    AND `enable` = #{condition.enable}
                </if>
            </where>
            LIMIT 100
        </script>
    """)
    fun select(@Param("condition") @NonNull imageEntityCondition: ImageEntityCondition): List<ImageEntityReadonly>
}
