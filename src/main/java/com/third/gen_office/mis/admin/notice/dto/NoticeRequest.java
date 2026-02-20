package com.third.gen_office.mis.admin.notice.dto;

import com.third.gen_office.mis.admin.notice.Notice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Notice}
 */
@Value
public class NoticeRequest implements Serializable {
    @NotBlank(message = "제목은 필수입니다.")
    String title;
    String content;
    String dispStartDate;
    String dispEndDate;
    @Pattern(message = "값은 'Y' 또는 'N'이어야 합니다.", regexp = "[YN]")
    String popupYn;
    @Pattern(message = "값은 'Y' 또는 'N'이어야 합니다.", regexp = "[YN]")
    String useYn;
}