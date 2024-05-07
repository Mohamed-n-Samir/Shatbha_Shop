import { useQuery } from "@tanstack/react-query";
import axios from "axios";

const useQueryCustom = (key, path, config = {}, params = null) => {
    const response = async () => {
        const data = await axios.get(
            `${import.meta.env.VITE_REACT_BACKEND_URL}/${path}`,
            params
        );

        return data;
    };

    return useQuery({
        queryKey: key,
        queryFn: response,
        ...config,
    });
};

export default useQueryCustom;
