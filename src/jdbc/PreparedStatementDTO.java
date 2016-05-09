package jdbc;

public class PreparedStatementDTO {
    private Object value;
            private int position;
            private int datatype;
            public Object getValue() {
                    return value;
            }
            public void setValue(Object value) {
                    this.value = value;
            }
            public int getPosition() {
                    return position;
            }
            public void setPosition(int position) {
                    this.position = position;
            }
            public int getDatatype() {
                    return datatype;
            }
            public void setDatatype(int datatype) {
                    this.datatype = datatype;
            }
}
