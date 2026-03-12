package com.third.gen_office.mis.admin.notice.dto;

import com.third.gen_office.domain.notice.NoticeEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link NoticeEntity}
 */
@Value
public class NoticeRequest implements Serializable {
    Integer requestId;
    Integer noticeId;
    @NotBlank(message = "?쒕ぉ? ?꾩닔?낅땲??")
    String title;
    String content;
    Integer fileSetId;
    String dispStartDate;
    String dispEndDate;
    @Pattern(message = "媛믪? 'Y' ?먮뒗 'N'?댁뼱???⑸땲??", regexp = "[YN]")
    String popupYn;
    @Pattern(message = "媛믪? 'Y' ?먮뒗 'N'?댁뼱???⑸땲??", regexp = "[YN]")
    String useYn;
}
