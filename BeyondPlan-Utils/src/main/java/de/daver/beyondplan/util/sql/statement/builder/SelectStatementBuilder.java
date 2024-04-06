package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.keywordBuilder.*;

public class SelectStatementBuilder extends StatementBuilder implements FromKeyWordBuilder<SelectStatementBuilder>,
                                                                        TargetKeyWordBuilder<SelectStatementBuilder>,
                                                                        CountKeyWordBuilder<SelectStatementBuilder>,
                                                                        MaxKeyWordBuilder<SelectStatementBuilder>,
                                                                        AsKeyWordBuilder<SelectStatementBuilder> {

    public SelectStatementBuilder() {
        super("SELECT");
    }

}
