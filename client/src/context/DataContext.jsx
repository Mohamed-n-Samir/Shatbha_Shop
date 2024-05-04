import { createContext, useEffect, useReducer } from "react";
import axios from "axios";
import { useQuery } from "@tanstack/react-query";

const token = localStorage.getItem("jwt")
console.log(token);

if(token){
	axios.interceptors.request.use(
		config => {
			config.headers.Authorization = `Bearer ${token}`
			return config;
		},
		error => {
			return Promise.reject(error);
		}
	)
}



const ACTIONS = {
	LOGIN: "LOGIN",
	LOGOUT: "LOGOUT",
};

const DataContext = createContext();

export const reducer = (state, action) => {
	switch (action.type) {
		case ACTIONS.LOGIN:
			return { user: action.payload };
		case ACTIONS.LOGOUT:
			return { user: "none" };
		default:
			return state;
	}
};

export const DataProvider = ({ children }) => {
	const [state, dispatch] = useReducer(reducer, { user: null });

	const { data, isSuccess,refetch } = useQuery({
		queryKey: ["userData"],
		queryFn: fetchUserData,
		refetchOnWindowFocus: false,
		refetchOnMount: true,
		keepPreviousData: true,
	});

	useEffect(() => {
		if (data && isSuccess) {
			console.log("elhelw halawa");
			console.log(data)
			dispatch({
				type: ACTIONS.LOGIN,
				payload: data,
			});
		}
	}, [data]);

	return (
		<DataContext.Provider value={{ ...state, dispatch,refetch }}>
			{children}
		</DataContext.Provider>
	);
};

export default DataContext;



const fetchUserData = async () => {
	try {
		const res = await axios.get(
			`${import.meta.env.VITE_REACT_BACKEND_URL}/getUserData`
		);
		console.log(res)
		if (res.data && !res.data.error) {
			console.log(res.data);
			return res.data.data.user;
		} else if (res.data.error) {
			return "none";
		}
	} catch (error) {
		if (!error.response) {
			return "none";
		} else {
			return "none";
		}
	}
};
