import apiRequest from "../client/client"
import type { UrlRequest, UrlResponse } from "../dto/url-request"
import type { WebResponse } from "../dto/web-response"

export const shortenUrl = async(data: UrlRequest): Promise<WebResponse<UrlResponse>> => {
  try {
    return await apiRequest('/shorten','POST',data);
  } catch(error) {
    console.error("Failed to shortening URL", error);
    return null;
  }
}
