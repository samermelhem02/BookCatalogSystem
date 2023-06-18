package mobi.foo.bookCatalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FooResponse {
    private String message;
    private boolean statues;
    private Object data;

    public FooResponse(){}
}
