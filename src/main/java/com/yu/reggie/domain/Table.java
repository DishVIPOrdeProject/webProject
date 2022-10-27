package com.yu.reggie.domain;

import com.yu.reggie.function.Methods;

import java.util.List;
import java.util.Objects;

    public class Table {
        private int id;

        public Table(int id) {
            this.id = id;
        }

        public Table(List<String> sqlLine) {
            try {
                Methods.checkSqlLineSize(sqlLine, 1);
                this.id = Integer.parseInt(sqlLine.get(0));
            } catch (Exception e) {
                e.printStackTrace();
                this.id = -1;
            }
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return String.format("Table{id=%d}", id);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Table table = (Table) obj;
            return id == table.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
