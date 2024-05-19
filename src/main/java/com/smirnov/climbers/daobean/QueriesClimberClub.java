package com.smirnov.climbers.daobean;

import lombok.Getter;

/**
 * Содержит запросы к клубу альпинистов.
 */
@Getter
public enum QueriesClimberClub {
    /**
     * Возвращает список групп, открытых для записи.
     */
    GET_GROUP_OPEN_RECORD("""
            SELECT gc
            FROM GroupClimbers gc
            WHERE gc.isOpen=true
            """),
    /**
     * Возвращает список названий гор, и количество альпинистов, покоривших ее.
     */

    GET_MOUNTAIN_NAME_AND_COUNT_CLIMBER(
            """
            SELECT tb_mountains.mountain_name
            FROM tb_mountains
            JOIN  tb_groups_climbers ON tb_groups_climbers.mountain_id=tb_mountains.id
            JOIN tb_records_climbing ON tb_groups_climbers.id=tb_records_climbing.group_climbers_id
            GROUP BY tb_mountains.mountain_name
            HAVING SUM( tb_records_climbing.count_climbers) > ?
            """),

    /**
     * Возвращает фамилии и email альпинистов в отсортированном по фамилии виде, которые не совершали восхождений за последний год. +++++++
     */
    GET_SECOND_NAME_AND_EMAIL_CLIMBER("""
            SELECT tb_climbers.last_name, tb_climbers.email
            FROM tb_climbers
            LEFT JOIN tb_climber_group_climbers ON tb_climber_group_climbers.climber_id=tb_climbers.id
            LEFT JOIN tb_groups_climbers ON tb_climber_group_climbers.group_climbers_id=tb_groups_climbers.id
            LEFT JOIN tb_records_climbing ON tb_records_climbing.group_climbers_id=tb_groups_climbers.id
            GROUP BY tb_climbers.id HAVING MAX(tb_records_climbing.finish) IS NULL
            OR MAX(tb_records_climbing.finish)<'?'
            ORDER BY tb_climbers.last_name
            LIMIT ? OFFSET ?"""),

    /**
     * По ФИО руководителя выводит идентификаторы групп, где количество покоривших гору больше заданного значения.
     */
    GET_ID_GROUP_BY_SUPERVISOR
            ("""
             SELECT DISTINCT sv.id
             FROM RecordClimbing trc
             JOIN trc.groupClimbers tgc
             JOIN tgc.supervisor sv
             WHERE sv.firstName ILIKE ?1
             AND sv.lastName ILIKE ?2
             AND sv.surName ILIKE ?3
             AND trc.countClimbers> ?4
             """
                    /*"""
            SELECT DISTINCT tb_groups_climbers.id
            FROM tb_groups_climbers
            JOIN tb_supervisors ON tb_supervisors.id=tb_groups_climbers.supervisor_id
            JOIN tb_records_climbing ON tb_records_climbing.group_climbers_id=tb_groups_climbers.id
            WHERE first_name ILIKE ?
            AND last_name ILIKE ?
            AND surname ILIKE ?
            AND tb_records_climbing.count_climbers>?
            """*/),

    /**
     * Возвращает список восхождений, которые осуществлялись в заданный период времени.
     **/
    GET_RECORD_CLIMBING_BY_INTERVAL("""
            SELECT rc
            FROM RecordClimbing rc
            WHERE (rc.finish <= ?1 AND rc.finish >= ?2)
            OR (rc.start <= ?3 AND rc.start>= ?4)
            """),

    /**
     * Возвращает список походов альпиниста в заданный период времени
     */
    GET_CLIMBING_CLIMBER_FOR_PERIOD("""
            SELECT tb_climbers.id, tb_groups_climbers.id
            "FROM tb_climbers
            "JOIN tb_climber_group_climbers ON tb_climber_group_climbers.climber_id=tb_climbers.id
            "JOIN tb_groups_climbers ON tb_groups_climbers.id=tb_climber_group_climbers.group_climbers_id
            "WHERE tb_climbers.id=?
            "AND (tb_groups_climbers.start_date>=? AND tb_groups_climbers.finish_date<=?)
            """);
    /**
     * Описание запроса.
     */
    private final String querySQL;

    /**
     * Конструктор создает запрос в БД.
     *
     * @param querySQL Описание запроса.
     */
    QueriesClimberClub(String querySQL) {
        this.querySQL = querySQL;
    }
}


