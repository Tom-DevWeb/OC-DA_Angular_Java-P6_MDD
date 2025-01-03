export interface ArticleResponse {
  id: number
  title: string
  content: string
  author: string
  theme: {
    id: number,
    title: string,
    description: string
  }
  createdAt: Date
}
