import apiRequest from "../client/client"
import type { UrlRequest, UrlResponse } from "../dto/url-request"
import type { WebResponse } from "../dto/web-response"

export const shortenUrl = async(data: UrlRequest): Promise<WebResponse<UrlResponse>> => {
    return apiRequest<WebResponse<UrlResponse>>(
        "/shorten",
        "POST",
        data,
    );
}
