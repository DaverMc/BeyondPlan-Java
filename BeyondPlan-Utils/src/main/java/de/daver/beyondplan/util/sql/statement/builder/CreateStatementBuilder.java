package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.keywordBuilder.ColumnsKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.IfKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.NameKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.OnKeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.node.ColumnType;
import de.daver.beyondplan.util.sql.statement.node.*;

public class CreateStatementBuilder extends StatementBuilder implements ColumnsKeyWordBuilder<CreateStatementBuilder>,
                                                                        NameKeyWordBuilder <CreateStatementBuilder>,
                                                                        IfKeyWordBuilder<CreateStatementBuilder>,
                                                                        OnKeyWordBuilder<CreateStatementBuilder> {

    public CreateStatementBuilder(Creatable creatable) {
        super("CREATE");
        keyWords.add(creatable);
    }

    public interface Creatable extends KeyWord{

        Creatable TABLE = () -> "TABLE";
        Creatable INDEX = () -> "INDEX";

    }
}
