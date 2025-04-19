
const prefix = import.meta.env.VITE_URL_PREFIX;

export const Urls = (param) => ({
  ...(
    {
      customers: {
        findAll_Post: `${prefix}customers`,
        findById_Put_Delete: `${prefix}customers/${param}`,
        findByEmail: `${prefix}customers/findbyemail/${param}`,
        findByIdNameCreditLimit: `${prefix}customers/idnamecreditlimit`,
      },
      employees: {
        findAll_Post: `${prefix}employees`,
        findById_Put_Delete: `${prefix}employees/${param}`,
        findByEmail: `${prefix}employees/findbyemail/${param}`,
        findByEmployeesIds: `${prefix}employees/employeeslist`,
        findByIdNames: `${prefix}employees/idname`,
      },
      offices: {
        findAll_Post: `${prefix}offices`,
        findById_Put_Delete: `${prefix}offices/${param}`,
        findByOfficeIds: `${prefix}offices/idcity`,
      },
      orderdetails: {
        Post: `${prefix}orderdetails`,
        findAll: `${prefix}orderdetails`,
        findByOrderId: `${prefix}orderdetails/orderid/${param}`,
        findByProductid: `${prefix}orderdetails/productid/${param}`,
      },
      orders: {
        findAll_Post: `${prefix}orders`,
        findById_Put_Delete: `${prefix}orders/${param}`,
        findByDate: `${prefix}orders/date/${param}`,
        findByRequiredDate: `${prefix}orders/requireddate/${param}`,
        findByShippedDate: `${prefix}orders/shippeddate/${param}`,
        findByStatus: `${prefix}orders/status/${param}`,
        findByCustomerId: `${prefix}orders/customerid/${param}`,
        findByIdStatus: `${prefix}orders/idstatus`,
      },
      payments: {
        findAll_Post: `${prefix}payments`,
        findByCustomerId: `${prefix}payments/customerid/${param}`,
        findByCheckNumber: `${prefix}payments/checknumber/${param}`,
        put_Delete: `${prefix}payments/${param}`,
      },
      productlines: {
        findAll_Post: `${prefix}productlines`,
        findByProductLine_Put_Delete: `${prefix}productlines/${param}`,
        findByProductLineList: `${prefix}productlines/productlinelist`,
      },
      products: {
        findAll_Post: `${prefix}products`,
        findById: `${prefix}products/id/${param}`,
        findByName: `${prefix}products/name/${param}`,
        findByProductLine: `${prefix}products/productline/${param}`,
        findByScale: `${prefix}products/scale/${param}`,
        findByVender: `${prefix}products/vendor/${param}`,
        put_Delete: `${prefix}products/${param}`,
        findByIdNameQuantityInStock: `${prefix}products/idnamequantityinstock`,
      },
      auth: {
        login_Post: `${prefix}auth/login`,
        refresh_Post: `${prefix}auth/refresh`,
        register_Post: `${prefix}auth/register`,
      },
      users: {
        findAll: `${prefix}users`,
        delete: `${prefix}users/${param}`,
      },
      calendar: {
        findAll_Post: `${prefix}calendar`,
        put_Delete: `${prefix}calendar/${param}`,
      }
    }
  )
})
