package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.ColumnType;
import de.daver.beyondplan.util.sql.statement.KeyWord;
import de.daver.beyondplan.util.sql.statement.node.ColumnsNode;
import de.daver.beyondplan.util.sql.statement.node.IfNode;

import java.util.ArrayList;
import java.util.List;

public class CreateStatementBuilder extends StatementBuilder {

    public CreateStatementBuilder(Creatable creatable) {
        super("CREATE");
        keyWords.add(creatable);
    }

    public CreateStatementBuilder ifCondition(KeyWord.Condition condition) {
        keyWords.add(new IfNode(condition));
        return this;
    }

    public CreateStatementBuilder name(String name) {
        keyWords.add(new KeyWord.Name(name));
        return this;
    }

    public CreateStatementBuilder column(String name, ColumnType type, boolean primaryKey) {
        keyWords.add(new KeyWord.Column(name, type, primaryKey));
        return this;
    }

    public CreateStatementBuilder column(String name, ColumnType type) {
        return column(name, type, false);
    }

    public CreateStatementBuilder on(String table, String column) {
        return this;
    }

    @Override
    protected void process() {
        List<KeyWord> columnKeywords = keyWords.stream().filter(keyWord -> keyWord instanceof KeyWord.Column).toList();
        keyWords.removeAll(columnKeywords);
        keyWords.add(new ColumnsNode(columnKeywords.stream().map(keyWord -> (KeyWord.Column) keyWord).toList()));
    }

    public interface Creatable extends KeyWord{

        Creatable TABLE = () -> "TABLE";
        Creatable INDEX = () -> "INDEX";

    }
}
