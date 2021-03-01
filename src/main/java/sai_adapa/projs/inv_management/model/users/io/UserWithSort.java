package sai_adapa.projs.inv_management.model.users.io;

import lombok.Getter;
import lombok.Setter;
import sai_adapa.projs.inv_management.tools.SortDetails;

import java.util.List;

@Getter
@Setter
public class UserWithSort {
    String userEmail;
    List<SortDetails> sortDetailsList;
}
