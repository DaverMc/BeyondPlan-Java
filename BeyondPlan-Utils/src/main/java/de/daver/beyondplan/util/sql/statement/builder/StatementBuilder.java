package de.daver.beyondplan.util.sql.statement.builder;

import de.daver.beyondplan.util.sql.statement.Statement;
import de.daver.beyondplan.util.sql.statement.keywordBuilder.KeyWordBuilder;
import de.daver.beyondplan.util.sql.statement.node.Command;
import de.daver.beyondplan.util.sql.statement.node.KeyWord;

import java.util.ArrayList;
import java.util.List;

public class StatementBuilder implements KeyWordBuilder {

    protected final List<KeyWord> keyWords;

    protected StatementBuilder() {
        keyWords = new ArrayList<>();
    }

    protected StatementBuilder(String keyWord) {
        this();
        keyWords.add(new Command(keyWord));
    }

    public String toString() {
        return STR."\{String.join(" ", keyWords.stream().map(KeyWord::keyword).toList())};";
    }

    public Statement build() {
        return new Statement(toString());
    }

    public static CreateStatementBuilder create(CreateStatementBuilder.Creatable creatable) {
        return new CreateStatementBuilder(creatable);
    }

    public static InsertIntoStatementBuilder insertInto() {
        return new InsertIntoStatementBuilder();
    }

    public static UpdateStatementBuilder update() {
        return new UpdateStatementBuilder();
    }

    public static DeleteStatementBuilder delete() {
        return new DeleteStatementBuilder();
    }

    public static SelectStatementBuilder select() {
        return new SelectStatementBuilder();
    }

    public static AlterStatementBuilder alter(CreateStatementBuilder.Creatable creatable) {
        return new AlterStatementBuilder(creatable);
    }

    public static DropStatementBuilder drop(CreateStatementBuilder.Creatable creatable) {
        return new DropStatementBuilder(creatable);
    }

    @Override
    public List<KeyWord> keyWords() {
        return keyWords;
    }

}
