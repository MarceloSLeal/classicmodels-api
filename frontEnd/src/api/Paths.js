
const prefix = "http://localhost:8080/";

export const Urls = (param) => ({
    ...(
        {
            customers: {
                findAll: `${prefix}customers${param}`,
            }
        }
    )
})
