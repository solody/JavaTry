package TryJson.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Row {
    private Long date;
    private String bundle;

    private BigDecimal revenue;
}
